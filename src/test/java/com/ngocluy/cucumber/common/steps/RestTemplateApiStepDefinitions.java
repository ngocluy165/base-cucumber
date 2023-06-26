package com.ngocluy.cucumber.common.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.common.contexts.RestTemplateApiContext;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;

public class RestTemplateApiStepDefinitions {
    @Autowired
    private RestTemplateApiContext apiContext;

    @Autowired
    private TransformStringContext transformStringContext;

    @Autowired
    private ObjectMapper objectMapper;
//
//    @When("I make POST Rest API call to url {string} with payload")
//    public void postApi(final String rawUrl, final String rawPayload) throws Throwable {
//        final String url = transformStringContext.transform(rawUrl);
//        final String payload = transformStringContext.transform(rawPayload);
//        apiContext.callPostApi(url, payload, null);
//    }
//
//    @When("I make PUT Rest API call to url {string} with payload")
//    public void putApi(final String rawUrl, final String rawPayload) throws Throwable {
//        final String url = transformStringContext.transform(rawUrl);
//        final String payload = transformStringContext.transform(rawPayload);
//        apiContext.callPutApi(url, payload, null);
//    }
//
//    @When("I make PATCH Rest API call to url {string} with payload")
//    public void patchApi(final String rawUrl, final String rawPayload) throws Throwable {
//        final String url = transformStringContext.transform(rawUrl);
//        final String payload = transformStringContext.transform(rawPayload);
//        apiContext.callPatchApi(url, payload, null);
//    }
//
//    @When("I make GET Rest API call to url {string}")
//    public void getApi(final String rawUrl) throws Throwable {
//        final String url = transformStringContext.transform(rawUrl);
//        apiContext.callGetApi(url, null);
//    }
//
//    @When("I make DELETE Rest API call to url {string}")
//    public void deleteApi(final String rawUrl) throws Throwable {
//        final String url = transformStringContext.transform(rawUrl);
//        apiContext.callDeleteApi(url, null);
//    }
//
//    @Then("The response of previous request has status code is {int}")
//    public void checkStatusOfPreviousRequest(int status) {
//        assertThat(apiContext.getLastResponse().getStatusCode(), Matchers.equalTo(status));
//    }
//
//    @Then("The response of previous request has status code is {int} and payload is")
//    public void checkStatusAndPayloadOfPreviousRequest(int status, String rawPayload) throws Throwable {
//        final String payload = transformStringContext.transform(rawPayload);
//        assertThat(apiContext.getLastResponse().getStatusCode(), Matchers.equalTo(status));
//        final JsonNode expectedNode = objectMapper.readTree(payload);
//        final JsonNode actualNode = objectMapper.readTree(apiContext.getLastResponse().getBody());
//        assertThat(actualNode, Matchers.equalTo(expectedNode));
//    }
//
//    @Then("The response of previous request has status code is {int} and payload as below json path table")
//    public void checkStatusAndPayloadOfPreviousRequest(int status, DataTable rawJsonPathDataTable) throws Throwable {
//        final DataTable dataTable = transformStringContext.transform(rawJsonPathDataTable);
//        Map<String, String> jsonPathAndExpectedValueMap = dataTable.asMap();
//        ResponseEntity<String> response = apiContext.getLastResponse();
//        JsonPath jsonPathObj = JsonPath.from(response.getBody());
//        assertThat(response.getStatusCode(), Matchers.equalTo(status));
//        for (String jsonPath: jsonPathAndExpectedValueMap.keySet()) {
//            assertThat(
//                    jsonPathObj.get(jsonPath).toString(),
//                    Matchers.equalTo(jsonPathAndExpectedValueMap.get(jsonPath))
//            );
//        }
//    }
}
