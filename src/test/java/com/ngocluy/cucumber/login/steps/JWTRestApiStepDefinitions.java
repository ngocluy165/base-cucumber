package com.ngocluy.cucumber.login.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTRestApiStepDefinitions {
    @Autowired
    private RestApiContext apiContext;

    @Autowired
    private TransformStringContext transformStringContext;
    @Autowired
    private JWTLoginContext jwtLoginContext;

    @Autowired
    private ObjectMapper objectMapper;
    
    @When("I make POST Rest API call with authorization to url {string} with payload")
    public void postApiWithAuthorization(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPostApi(url, payload, jwtLoginContext.generateJWTAuthHeadersForLatestLoginUser());
    }

    @When("I make PUT Rest API call with authorization to url {string} with payload")
    public void putApiWithAuthorization(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPutApi(url, payload, jwtLoginContext.generateJWTAuthHeadersForLatestLoginUser());
    }

    @When("I make PATCH Rest API call with authorization to url {string} with payload")
    public void patchApiWithAuthorization(final String rawUrl, final String rawPayload) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        final String payload = transformStringContext.transform(rawPayload);
        apiContext.callPatchApi(url, payload, jwtLoginContext.generateJWTAuthHeadersForLatestLoginUser());
    }

    @When("I make GET Rest API call with authorization to url {string}")
    public void getApiWithAuthorization(final String rawUrl) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        apiContext.callGetApi(url, jwtLoginContext.generateJWTAuthHeadersForLatestLoginUser());
    }

    @When("I make DELETE Rest API call with authorization to url {string}")
    public void deleteApiWithAuthorization(final String rawUrl) throws Throwable {
        final String url = transformStringContext.transform(rawUrl);
        apiContext.callDeleteApi(url, jwtLoginContext.generateJWTAuthHeadersForLatestLoginUser());
    }
}
