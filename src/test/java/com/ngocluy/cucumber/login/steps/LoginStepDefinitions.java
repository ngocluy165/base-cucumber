package com.ngocluy.cucumber.login.steps;

import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import com.ngocluy.cucumber.login.contexts.RegisterContext;
import com.ngocluy.cucumber.login.services.LoginService;
import com.ngocluy.cucumber.login.services.RegisterService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.event.annotation.AfterTestExecution;

import java.util.Map;

public class LoginStepDefinitions {
    @Autowired
    private RegisterContext registerContext;
    @Autowired
    private JWTLoginContext jwtLoginContext;
    @Autowired
    private RestApiContext apiContext;
    @Autowired
    private RegisterService registerService;

    @Autowired
    private TransformStringContext transformStringContext;
    @Autowired
    private LoginService loginService;
    @When("^User register new account$")
    public void userWantsToRegister(
            DataTable table
    ) throws Throwable {
        DataTable transformedTable = transformStringContext.transform(table);
        Map<String, String> row = transformedTable.asMap();
        Response responseEntity = registerService.register(row, apiContext, registerContext);
        Assertions.assertThat(responseEntity.getStatusCode()).isIn(200, 201);

    }
    @When("User want to login with username {string} and password {string}")
    public void userWantsLogin(
            final String username,
            final String password
    ) throws Throwable {
        loginService.login(
                transformStringContext.transform(username),
                transformStringContext.transform(password),
                apiContext,
                jwtLoginContext
        );
    }

    @Then("User logins successfully")
    public void assertLoginSuccessfully() {
        final HttpStatus httpStatus = jwtLoginContext.getLatestLoginHttpStatus();
        Assertions.assertThat(httpStatus.value()).isIn(200, 201);
    }

    @AfterTestExecution
    public void deleteUser() {
        System.out.println("Delete User" + registerContext.getRegisterUserIds());

    }
}
