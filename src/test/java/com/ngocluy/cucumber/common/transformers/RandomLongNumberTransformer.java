package com.ngocluy.cucumber.common.transformers;

import io.cucumber.cucumberexpressions.Transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomLongNumberTransformer implements Transformer<String> {
    private final Map<String, String> transformedData = new HashMap<>();   //Key -> random string

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{random_from_([0-9]+)_to_([0-9]+)_long_number_[^\\}]*\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            int from = Integer.parseInt(matcher.group(2));
            int to = Integer.parseInt(matcher.group(3));
            replacements.put(
                    group,
                    transformedData.computeIfAbsent(group, key -> String.valueOf(from + (long) (Math.random() * (to - from))))
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

}
