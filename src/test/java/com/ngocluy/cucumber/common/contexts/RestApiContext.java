package com.ngocluy.cucumber.common.contexts;

import io.cucumber.spring.ScenarioScope;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ScenarioScope
@Component
public class RestApiContext {
    private final List<Response> responses = new ArrayList<>();


    public Response callPostApi(String url, String payload, Headers requestHeaders) {
        Headers headers = requestHeaders == null ? new Headers() : requestHeaders;
        Response response = RestAssured.given()
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(payload).post(URI.create(url));
        this.responses.add(response);
        return response;
    }


    public Response callPutApi(String url, String payload, Headers requestHeaders) {
        Headers headers = requestHeaders == null ? new Headers() : requestHeaders;
        Response response = RestAssured.given()
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(payload)
                .put(URI.create(url));
        this.responses.add(response);
        return response;
    }

    public Response callPatchApi(String url, String payload, Headers requestHeaders) {
        Headers headers = requestHeaders == null ? new Headers() : requestHeaders;
        Response response = RestAssured.given()
                .headers(headers)
                .header("Content-Type", "application/json")
                .body(payload)
                .patch(URI.create(url));
        this.responses.add(response);
        return response;
    }

    public Response callDeleteApi(String url, Headers requestHeaders) {
        Headers headers = requestHeaders == null ? new Headers() : requestHeaders;
        Response response = RestAssured.given()
                .headers(headers)
                .header("Content-Type", "application/json")
                .delete(URI.create(url));
        this.responses.add(response);
        return response;
    }

    public Response callGetApi(String url) {
        return this.callGetApi(url, null);
    }

    public Response callGetApi(String url, Headers requestHeaders) {
        Headers headers = requestHeaders == null ? new Headers() : requestHeaders;
        Response response = RestAssured.given()
                .headers(headers)
                .header("Content-Type", "application/json")
                .get(URI.create(url));
        this.responses.add(response);
        return response;
    }

    public Response getResponse(int index) {
        return responses.get(index);
    }

    public Response getFirstResponse() {
        return responses.get(0);
    }

    public Response getLastResponse() {
        return responses.get(responses.size() - 1);
    }

    public int getResponsesSize() {
        return responses.size();
    }
}
