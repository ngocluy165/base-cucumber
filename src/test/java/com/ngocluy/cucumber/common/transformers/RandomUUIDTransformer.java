package com.ngocluy.cucumber.common.transformers;

import io.cucumber.cucumberexpressions.Transformer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomUUIDTransformer implements Transformer<String> {
    private final Map<String, String> transformedData = new HashMap<>();   //Key -> random string

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{random_uuid_[^\\}]*\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            replacements.put(
                    group,
                    transformedData.computeIfAbsent(group, key -> UUID.randomUUID().toString())
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

}
