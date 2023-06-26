package com.ngocluy.cucumber.common.transformers;

import io.cucumber.cucumberexpressions.Transformer;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomAlphabeticStringTransformer implements Transformer<String> {
    private final Map<String, String> transformedData = new HashMap<>();   //Key -> random string

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{random_length_([1-9][0-9]*)_alphabetic_[^\\}]*\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            int length = Integer.parseInt(matcher.group(2));
            replacements.put(
                    group,
                    transformedData.computeIfAbsent(group, key -> RandomStringUtils.randomAlphabetic(length))
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

    public static void main(String[] args) {
        RandomAlphabeticStringTransformer stringTransformer = new RandomAlphabeticStringTransformer();
        String newString = stringTransformer.transformString("${random_length_12_alphabetic_1} a ${random_length_12_alphabetic_1} b ");
        System.out.println(newString);
    }
}
