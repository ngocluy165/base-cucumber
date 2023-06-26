package com.ngocluy.cucumber.common.transformers;

import io.cucumber.cucumberexpressions.Transformer;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomAlphaNumericStringTransformer implements Transformer<String> {
    private final Map<String, String> transformedData = new HashMap<>();   //Key -> random string

    public String transform(String input) {
        return transformString(input);

    }


    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{random_length_([1-9][0-9]*)_alphanumeric_[^\\}]*\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            int length = Integer.parseInt(matcher.group(2));
            replacements.put(
                    group,
                    transformedData.computeIfAbsent(group, key -> RandomStringUtils.randomAlphanumeric(length))
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

}
