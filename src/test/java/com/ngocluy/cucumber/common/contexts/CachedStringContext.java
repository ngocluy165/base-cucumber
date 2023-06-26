package com.ngocluy.cucumber.common.contexts;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ScenarioScope
@Component
public class CachedStringContext {
    private Map<String, String> cachedVariables = new HashMap();

    public void addCache(String variableName, String variableValue) {
        this.cachedVariables.put(variableName, variableValue);
    }

    public String getCache(String variableName) {
        return cachedVariables.get(variableName);
    }
}
