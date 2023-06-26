package com.ngocluy.cucumber.common.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.ngocluy.cucumber.common.utils.StringTypeUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestApiStepDefinitions {
    @Autowired
    private RestApiContext apiContext;

    @Autowired
    private TransformStringContext transformStringContext;

    @Autowired
    private ObjectMapper objectMapper;

    @When("I make POST Rest API call to url {string} with payload")
    public void postApi(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPostApi(url, payload, null);
    }

    @When("I make PUT Rest API call to url {string} with payload")
    public void putApi(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPutApi(url, payload, null);
    }

    @When("I make PATCH Rest API call to url {string} with payload")
    public void patchApi(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPatchApi(url, payload, null);
    }

    @When("I make GET Rest API call to url {string}")
    public void getApi(final String rawUrl) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        apiContext.callGetApi(url);
    }

    @When("I make DELETE Rest API call to url {string}")
    public void deleteApi(final String rawUrl) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        apiContext.callDeleteApi(url, null);
    }

    @Then("The response of previous request has status code is {int}")
    public void checkStatusOfPreviousRequest(int status) {
        assertThat(apiContext.getLastResponse().getStatusCode(), Matchers.equalTo(status));
    }

    @Then("The response of previous request has status code is {int} and payload is")
    public void checkStatusAndPayloadOfPreviousRequest(int status, String rawPayload) throws Throwable {
        final String payload = transformStringContext.transform(rawPayload);
        assertThat(apiContext.getLastResponse().getStatusCode(), Matchers.equalTo(status));
        final JsonNode expectedNode = objectMapper.readTree(payload);
        final JsonNode actualNode = objectMapper.readTree(apiContext.getLastResponse().getBody().asString());
        assertThat(actualNode, Matchers.equalTo(expectedNode));
    }


    private void assertResponsePayloadFromRawDataTable(Response response, DataTable rawJsonPathDataTable) throws Throwable {
        final DataTable dataTable = transformStringContext.transform(rawJsonPathDataTable);
        assertResponsePayloadFromDataTable(response, dataTable);
    }

    public static void assertResponsePayloadFromDataTable(ObjectMapper objectMapper, Response response, DataTable dataTable) throws JsonProcessingException {
        Map<String, String> jsonPathAndExpectedValueMap = dataTable.asMap();
        JsonPath jsonPathObj = response.jsonPath();

        for (String jsonPath : jsonPathAndExpectedValueMap.keySet()) {
            if (isBoolean(jsonPathAndExpectedValueMap.get(jsonPath)) || isNumber(jsonPathAndExpectedValueMap.get(jsonPath))) {
                assertThat(jsonPathObj.get(jsonPath).toString(), Matchers.equalTo(jsonPathAndExpectedValueMap.get(jsonPath)));
            } else if (isJson(jsonPathAndExpectedValueMap.get(jsonPath))) {
                assertThat(objectMapper.readTree(objectMapper.writeValueAsString(jsonPathObj.get(jsonPath))), Matchers.equalTo(objectMapper.readTree(jsonPathAndExpectedValueMap.get(jsonPath))));
            } else {
                assertThat(jsonPathObj.get(jsonPath).toString(), Matchers.equalTo(jsonPathAndExpectedValueMap.get(jsonPath)));
            }
        }
    }

    public static void assertResponsePayloadFromRawDataTable(TransformStringContext transformStringContext, ObjectMapper objectMapper, Response response, DataTable rawJsonPathDataTable) throws Throwable {
        final DataTable dataTable = transformStringContext.transform(rawJsonPathDataTable);
        assertResponsePayloadFromDataTable(objectMapper, response, dataTable);
    }

    public static void assertLastResponsePayloadFromRawDataTable(RestApiContext apiContext, TransformStringContext transformStringContext, ObjectMapper objectMapper, DataTable rawJsonPathDataTable) throws Throwable {
        Response response = apiContext.getLastResponse();
        final DataTable dataTable = transformStringContext.transform(rawJsonPathDataTable);
        assertResponsePayloadFromDataTable(objectMapper, response, dataTable);
    }

    private void assertResponsePayloadFromDataTable(Response response, DataTable dataTable) throws Throwable {
        assertResponsePayloadFromDataTable(objectMapper, response, dataTable);
    }

    @Then("The response of previous request has status code is {int} and payload as below json path table")
    public void checkStatusAndPayloadOfPreviousRequest(int status, DataTable rawJsonPathDataTable) throws Throwable {
        Response response = apiContext.getLastResponse();
        assertThat(response.getStatusCode(), Matchers.equalTo(status));
        assertResponsePayloadFromDataTable(response, rawJsonPathDataTable);
    }

    @Then("The response of previous request has payload as below json path table")
    public void checkStatusAndPayloadOfPreviousRequest(DataTable rawJsonPathDataTable) throws Throwable {
        Response response = apiContext.getLastResponse();
        assertResponsePayloadFromDataTable(response, rawJsonPathDataTable);
    }
}
