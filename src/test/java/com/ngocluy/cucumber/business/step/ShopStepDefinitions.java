package com.ngocluy.cucumber.business.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.business.contexts.ShopContext;
import com.ngocluy.cucumber.business.services.ShopService;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.common.steps.RestApiStepDefinitions;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ShopStepDefinitions {
    @Autowired
    private RestApiContext apiContext;

    @Autowired
    private TransformStringContext transformStringContext;
    @Autowired
    private JWTLoginContext jwtLoginContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShopContext shopContext;

    @Autowired
    private ShopService shopService;
    
    @When("As a login user, I want to create a new Shop with payload in below table")
    public void createNewShopFromTable(final DataTable table) throws Throwable {
        DataTable transformedTable = transformStringContext.transform(table);
        Map<String, String> row = transformedTable.asMap();
        shopService.createShop(row, apiContext, jwtLoginContext, shopContext);
    }

    @Then("The shop was created successfully")
    public void shopIsCreated() throws Throwable {
        assertThat(apiContext.getLastResponse().getStatusCode()).isEqualTo(200);
    }


    @When("As a login user, I want to delete the previous created Shop")
    public void deletePreviousCreatedShop() throws Throwable {
        shopService.deleteShop(shopContext.getLastCreateShopId(),apiContext, jwtLoginContext, shopContext);
    }

    @When("As a login user, I want to delete the Shop with id {string}")
    public void deleteShop(String rawShopId) throws Throwable {
        String shopId = transformStringContext.transform(rawShopId);
        shopService.deleteShop(shopId,apiContext, jwtLoginContext, shopContext);
    }

    @When("As a login user, I fetch the shop with id {string}")
    public void getShop(String rawShopId) throws Throwable {
        String shopId = transformStringContext.transform(rawShopId);
        shopService.getShop(shopId, apiContext, jwtLoginContext);
    }

    @When("The shop has data as below table")
    public void verifyShop(DataTable rawDataTable) throws Throwable {
        RestApiStepDefinitions.assertLastResponsePayloadFromRawDataTable(apiContext, transformStringContext, objectMapper, rawDataTable);
    }

    @Then("The shop was deleted successfully")
    public void shopIsDeleted() throws Throwable {
        assertThat(apiContext.getLastResponse().getStatusCode()).isEqualTo(200);
    }

    @Then("The shop id {string} does not exist")
    public void shopDoesNotExist(String rawShopId) throws Throwable {
        String shopId = transformStringContext.transform(rawShopId);
        assertThat(shopContext.getShopById(shopId)).isNull();
    }


    @When("I delete the Shop with ID {string} from login username {string}")
    public void deleteShopByIdAndUser(String rawShopId, String rawUsername) throws Throwable {
        final String shopId = transformStringContext.transform(rawShopId);
        final String username = transformStringContext.transform(rawUsername);

        shopService.deleteShopForUser(username, shopId, apiContext, jwtLoginContext, shopContext);
    }

}
