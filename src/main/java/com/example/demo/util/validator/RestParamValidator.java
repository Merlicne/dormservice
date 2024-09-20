package com.example.demo.util.validator;

public class RestParamValidator {
    
    private RestParamValidator() {
        throw new IllegalStateException("Utility class");
    }


    public static String validateIncludedDeleted(String includedDeleted) {
        if (includedDeleted != null && !includedDeleted.equals("true") && !includedDeleted.equals("false")) {
            throw new IllegalArgumentException("includedDeleted must be true or false");
        }
        if (includedDeleted == null) {
            includedDeleted = "false";
        }
        return includedDeleted;
    }
}
