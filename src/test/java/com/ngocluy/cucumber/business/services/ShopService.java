package com.ngocluy.cucumber.business.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngocluy.cucumber.business.contexts.CachedShop;
import com.ngocluy.cucumber.business.contexts.ShopContext;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Service
public class ShopService {
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public ShopService(
            @Value("${service.api.endpoint}") String apiUrl,
            @Autowired ObjectMapper objectMapper
    ) {
        this.apiUrl = apiUrl;
        this.objectMapper = objectMapper;
    }

    public void createShop(
            Map payload,
            RestApiContext apiContext,
            JWTLoginContext currentLoginContent,
            ShopContext shopContext
    ) throws JsonProcessingException {
        this.createShop(objectMapper.writeValueAsString(payload), apiContext, currentLoginContent, shopContext);
    }

    public void createShop(
            String payload,
            RestApiContext apiContext,
            JWTLoginContext currentLoginContent,
            ShopContext shopContext
    ) throws JsonProcessingException {
        Response response = apiContext.callPostApi(
                apiUrl + "/api/shops",
                payload,
                currentLoginContent.generateJWTAuthHeadersForLatestLoginUser()
        );
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
            String id = response.getBody().jsonPath().get("id");
            shopContext.add(new CachedShop(id, currentLoginContent.getLatestLoginUsername()));
        }
    }

    public void createShopForUser(
            Map payload,
            String username,
            RestApiContext apiContext,
            JWTLoginContext jwtLoginContext,
            ShopContext shopContext
    ) throws JsonProcessingException {
        this.createShopForUser(username, objectMapper.writeValueAsString(payload), apiContext, jwtLoginContext, shopContext);
    }

    public void createShopForUser(
            String username,
            String payload,
            RestApiContext apiContext,
            JWTLoginContext jwtLoginContext,
            ShopContext shopContext
    ) throws JsonProcessingException {
        assertThat(jwtLoginContext.userJwtToken(username)).isNotNull();
        Response response = apiContext.callPostApi(
                apiUrl + "/api/shops",
                payload,
                jwtLoginContext.generateJWTAuthHeadersForUser(username)
        );
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
            String id = response.getBody().jsonPath().get("id");
            shopContext.add(new CachedShop(id, username));
        }
    }

    public void deleteShop(
            String shopId,
            RestApiContext apiContext,
            JWTLoginContext currentLoginContent,
            ShopContext shopContext
    ) {
        Response response = apiContext.callDeleteApi(apiUrl + "/api/shops/" + shopId, currentLoginContent.generateJWTAuthHeadersForLatestLoginUser());
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
            String id = response.getBody().jsonPath().get("id");
            shopContext.removeShopById(id);
        }
    }

    public Response getShop(
            String shopId,
            RestApiContext apiContext,
            JWTLoginContext currentLoginContent
    ) {

        return apiContext.callGetApi(apiUrl + "/api/shops/" + shopId, currentLoginContent.generateJWTAuthHeadersForLatestLoginUser());
    }


    public void deleteShopForUser(
            String username,
            String shopId,
            RestApiContext apiContext,
            JWTLoginContext jwtLoginContext,
            ShopContext shopContext
    ) {
        assertThat(jwtLoginContext.userJwtToken(username)).isNotNull();
        Response response = apiContext.callDeleteApi(apiUrl + "/api/shops/" + shopId, jwtLoginContext.generateJWTAuthHeadersForUser(username));
        if (HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
            String id = response.getBody().jsonPath().get("id");
            shopContext.getShopIds().remove(id);
        }
    }
}
