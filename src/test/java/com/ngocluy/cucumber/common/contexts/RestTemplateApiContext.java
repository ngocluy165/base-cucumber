package com.ngocluy.cucumber.common.contexts;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ScenarioScope
@Component
public class RestTemplateApiContext {
    private final List<ResponseEntity<String>> responses = new ArrayList<>();

    private RestTemplate restTemplate;


    public RestTemplateApiContext(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private ResponseEntity<String> callExchangeApi(HttpMethod httpMethod, String url, String payload, HttpHeaders requestHeaders) {
        HttpHeaders headers = requestHeaders == null ? new HttpHeaders() : requestHeaders;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(payload, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                httpMethod,
                entity ,
                String.class
        );
        this.responses.add(responseEntity);
        return responseEntity;
    }


    private ResponseEntity<String> callNoRequestPayloadApi(HttpMethod httpMethod, String url, HttpHeaders requestHeaders) {
        HttpHeaders headers = requestHeaders == null ? new HttpHeaders() : requestHeaders;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity(null, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                httpMethod,
                entity ,
                String.class
        );
        this.responses.add(responseEntity);
        return responseEntity;
    }

    public ResponseEntity<String> callPostApi(String url, String payload, HttpHeaders requestHeaders) {
        return this.callExchangeApi(HttpMethod.POST, url, payload, requestHeaders);
    }


    public ResponseEntity<String> callPutApi(String url, String payload, HttpHeaders requestHeaders) {
        return this.callExchangeApi(HttpMethod.PUT, url, payload, requestHeaders);
    }

    public ResponseEntity<String> callPatchApi(String url, String payload, HttpHeaders requestHeaders) {
        return this.callExchangeApi(HttpMethod.PATCH, url, payload, requestHeaders);
    }

    public ResponseEntity<String> callDeleteApi(String url, HttpHeaders requestHeaders) {
        return this.callNoRequestPayloadApi(HttpMethod.DELETE, url, requestHeaders);
    }

    public ResponseEntity<String> callGetApi(String url, HttpHeaders requestHeaders) {
        return this.callNoRequestPayloadApi(HttpMethod.GET, url, requestHeaders);
    }

    public ResponseEntity<String> getResponse(int index) {
        return responses.get(index);
    }

    public ResponseEntity<String> getFirstResponse() {
        return responses.get(0);
    }

    public ResponseEntity<String> getLastResponse() {
        return responses.get(responses.size() - 1);
    }

    public int getResponsesSize() {
        return responses.size();
    }
}
