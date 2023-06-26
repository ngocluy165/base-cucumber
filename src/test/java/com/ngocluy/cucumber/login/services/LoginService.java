package com.ngocluy.cucumber.login.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import io.restassured.response.Response;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {
    private final String loginUrl;
    private final ObjectMapper objectMapper;


    public LoginService(
            @Value("${service.api.jwtLoginUrl}") String loginUrl,
            @Autowired final ObjectMapper objectMapper

    ) {
        this.loginUrl = loginUrl;
        this.objectMapper = objectMapper;
    }

    public Response login(
            String username,
            String password,
            RestApiContext apiContext,
            JWTLoginContext jwtLoginContext
    ) throws JsonProcessingException {

        Map<String, String> payload = Maps.of("username", username, "password", password);
        Response responseEntity = apiContext.callPostApi(
                loginUrl,
                objectMapper.writeValueAsString(payload),
                null
        );
        if (responseEntity.getStatusCode() >= 200 && responseEntity.getStatusCode() < 300) {
            String token = responseEntity.getBody().jsonPath().get("token");
            jwtLoginContext.addToken(username, token);
        }
        jwtLoginContext.addHttpStatus(username, responseEntity.getStatusCode());
        return responseEntity;

    }
}
