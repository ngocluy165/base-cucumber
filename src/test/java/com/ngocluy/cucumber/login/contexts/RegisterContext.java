package com.ngocluy.cucumber.login.contexts;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@ScenarioScope
@Component
public class RegisterContext {
    private final Set<String> registerUserIds = new HashSet<>();
    public void add(String userId) {
        registerUserIds.add(userId);
    }
    public Set<String> getRegisterUserIds() {
        return registerUserIds;
    }
}
