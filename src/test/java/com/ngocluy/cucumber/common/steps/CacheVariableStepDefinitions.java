package com.ngocluy.cucumber.common.steps;

import com.ngocluy.cucumber.common.contexts.CachedStringContext;
import com.ngocluy.cucumber.common.transformers.TransformStringContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class CacheVariableStepDefinitions {
    @Autowired
    private TransformStringContext transformStringContext;

    @Autowired
    private CachedStringContext cachedStringContext;

    @Given("I have a cache with a variable {string} and String value {string}")
    public void postApi(final String rawVariableName, final String rawVariableValue) throws Throwable {
        final String variableName = transformStringContext.transform(rawVariableName);
        final String variableValue = transformStringContext.transform(rawVariableValue);
        cachedStringContext.addCache(variableName, variableValue);
    }

    @Given("I want to cache a list of variables and String values from below table")
    public void postApi(DataTable rawJsonPathDataTable) throws Throwable {
        final DataTable dataTable = transformStringContext.transform(rawJsonPathDataTable);
        Map<String, String> variableAndValueMap = dataTable.asMap();
        for (String variableName: variableAndValueMap.keySet()) {
            cachedStringContext.addCache(variableName, variableAndValueMap.get(variableName));
        }
    }

}
