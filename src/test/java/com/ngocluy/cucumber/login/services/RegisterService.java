package com.ngocluy.cucumber.login.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.login.contexts.RegisterContext;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegisterService {
    private String registerApi;
    private final ObjectMapper objectMapper;


    public RegisterService(
            @Autowired ObjectMapper objectMapper,
            @Value("${service.api.register}") String registerApi
    ) {
        this.registerApi = registerApi;
        this.objectMapper = objectMapper;
    }
    public Response register(Map<?, ?> payload, RestApiContext apiContext, RegisterContext registerContext) throws JsonProcessingException {
        Response response = apiContext.callPostApi(registerApi, objectMapper.writeValueAsString(payload), null);
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
            String id = response.getBody().jsonPath().get("id");
            registerContext.add(id);
        }
        return response;

    }
}
