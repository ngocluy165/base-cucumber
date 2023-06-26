package com.ngocluy.cucumber.common.transformers;

import io.cucumber.cucumberexpressions.Transformer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomFloatNumberTransformer implements Transformer<String> {
    private final Map<String, String> transformedData = new HashMap<>();   //Key -> random string

    public String transform(String input) {
        return transformString(input);

    }

    private String transformString(String input) {
        final String[] inputCopy = {input};
        Map<String, String> replacements = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\$\\{random_from_([0-9]+)_to_([0-9]+)_float_number_[^\\}]*\\})").matcher(input);
        while (matcher.find()) {
            String group = matcher.group(1);
            double from = Integer.parseInt(matcher.group(2));
            double to = Integer.parseInt(matcher.group(3));
            String randomNumberStr =  BigDecimal.valueOf(from + new Random().nextFloat() * (to - from)).setScale(7, RoundingMode.UP).toString();
            replacements.put(
                    group,
                    transformedData.computeIfAbsent(group, key -> randomNumberStr)
            );
        }
        replacements.forEach((key, value) -> inputCopy[0] = inputCopy[0].replace(key, value));
        return inputCopy[0];
    }

}
