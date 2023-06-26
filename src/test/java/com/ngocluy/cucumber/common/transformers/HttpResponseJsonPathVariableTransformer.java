package com.ngocluy.cucumber.common.transformers;

import com.ngocluy.cucumber.common.contexts.RestApiContext;
import io.cucumber.cucumberexpressions.Transformer;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpResponseJsonPathVariableTransformer implements Transformer<String> {

    private final RestApiContext restApiContext;

    public HttpResponseJsonPathVariableTransformer(RestApiContext restApiContext) {
        this.restApiContext = restApiContext;
    }

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        // example: ${responses[-1].id}, ${responses[-1].[0].id}. -1: index of payload, remaining: jsonpath
        Matcher matcher = Pattern.compile("(\\$\\{responses\\[(-?[0-9]+)\\]\\.([a-zA-Z0-9\\_\\[\\]\\.]+)\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            int idx = Integer.parseInt(matcher.group(2));
            String jsonpath = matcher.group(3);
            if (idx == -1) {
                idx = restApiContext.getResponsesSize() - 1;
            }
            final Response response = restApiContext.getResponse(idx);
            final String resolvedValue = response.getBody().jsonPath().getString(jsonpath);
            if (resolvedValue == null) {
                throw new RuntimeException("Input " + group + ":Can not get null value from json path " + jsonpath + " for response " + idx);
            }
            replacements.put(
                    group,
                    resolvedValue
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

    public static void main(String[] args) {
        RestApiContext restApiContext = new RestApiContext();
        HttpResponseJsonPathVariableTransformer cachedStringVariableTransformer = new HttpResponseJsonPathVariableTransformer(restApiContext);
        restApiContext.callPostApi("https://hungnt.free.beeceptor.com/todos", "{}", null);

        restApiContext.callGetApi("https://hungnt.free.beeceptor.com/todos");
        System.out.println(cachedStringVariableTransformer.transform("!!!${responses[-1].[0].id}"));
        System.out.println(cachedStringVariableTransformer.transform("!!!${responses[0].id}"));
    }
}
