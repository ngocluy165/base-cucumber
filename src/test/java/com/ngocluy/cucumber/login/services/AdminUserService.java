package com.ngocluy.cucumber.login.services;

import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    private final String apiUrl;

    public AdminUserService(@Value("${service.api.endpoint}") String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void deleteUser(String userId, RestApiContext apiContext, JWTLoginContext currentLoginContent) {
        apiContext.callDeleteApi(apiUrl + "/api/users/" + userId, currentLoginContent.generateJWTAuthHeadersForLatestLoginUser());
    }
}
