package com.ngocluy.cucumber.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringTypeUtils {

    public static boolean isJson(String str) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(str);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBoolean(String str) {
        return ("true".equals(str) || "false".equals(str));
    }
}
