package com.ngocluy.cucumber.common.transformers;

import com.ngocluy.cucumber.common.contexts.CachedStringContext;
import io.cucumber.cucumberexpressions.Transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CachedStringVariableTransformer implements Transformer<String> {

    private final CachedStringContext cachedStringContext;

    public CachedStringVariableTransformer(CachedStringContext cachedStringContext) {
        this.cachedStringContext = cachedStringContext;
    }

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{variable\\.([a-zA-Z0-9\\_]+)\\})").matcher(input);

        while (matcher.find()) {
            String group = matcher.group(1);
            String variableName = matcher.group(2);
            String resolveVariable = cachedStringContext.getCache(variableName);
            if (resolveVariable == null) {
                throw new RuntimeException("Input " + group + ":Can not get null value from variable " + variableName);
            }

            replacements.put(
                    group,
                    cachedStringContext.getCache(variableName)
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

    public static void main(String[] args) {
        CachedStringContext cachedStringContext = new CachedStringContext();
        CachedStringVariableTransformer cachedStringVariableTransformer = new CachedStringVariableTransformer(cachedStringContext);
        cachedStringContext.addCache("hello", "world");
        cachedStringContext.addCache("foo", "bar");
        System.out.println(cachedStringVariableTransformer.transform("!!!${variable.hello}${variable.foo}"));
    }
}
