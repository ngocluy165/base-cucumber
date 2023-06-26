package com.ngocluy.cucumber.login.contexts;

import io.cucumber.spring.ScenarioScope;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ScenarioScope
@Component
public class JWTLoginContext {
    private String latestLoginSuccessUsername;
    private String latestLoginUsername;
    private final Map<String, String> userNameToJwtTokenMap = new HashMap<>();
    private final Map<String, HttpStatus> userNameAndHttpStatusMap = new HashMap<>();
    public void addToken(String username, String token) {
        if (token != null && !token.isEmpty()) {
            userNameToJwtTokenMap.put(username, token);
            this.latestLoginSuccessUsername = username;
        }
    }

    public void addHttpStatus(String username, HttpStatus httpStatus) {
        this.userNameAndHttpStatusMap.put(username, httpStatus);
        this.latestLoginUsername = username;
    }

    public void addHttpStatus(String username, int httpStatus) {
        this.userNameAndHttpStatusMap.put(username, HttpStatus.valueOf(httpStatus));
        this.latestLoginUsername = username;
    }

    public String userJwtToken(String username) {
        return userNameToJwtTokenMap.get(username);
    }

    public HttpStatus getLoginHttpStatus(String username) {
        return userNameAndHttpStatusMap.get(username);
    }

    public String getLatestLoginSuccessUsername() {
        return latestLoginSuccessUsername;
    }

    public String getLatestLoginUsername() {
        return latestLoginUsername;
    }

    @Deprecated
    //("Turn on for spring Rest template. ")
    public HttpHeaders generateJWTAuthRestTemplateHeaderForLatestLoginUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken(latestLoginUsername));
        return headers;
    }

    public Headers generateJWTAuthHeadersForLatestLoginUser() {
        Headers headers = Headers.headers(new Header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken(latestLoginUsername)));
        return headers;
    }

    public Headers generateJWTAuthHeadersForUser(String username) {
        Headers headers = Headers.headers(new Header(HttpHeaders.AUTHORIZATION, "Bearer " + userJwtToken(username)));
        return headers;
    }


    public HttpStatus getLatestLoginHttpStatus() {
        return this.userNameAndHttpStatusMap.get(latestLoginUsername);
    }
}
