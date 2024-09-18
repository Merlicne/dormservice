package com.example.demo.util.validator;

import com.example.demo.exception.BadRequestException;

public class PasswordValidator {
    private PasswordValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Password is required");
        }
        int upperCase = 0;
        int lowerCase = 0;
        int digit = 0;

        String invalidChars = "*|,\":<>[]{}`\';()@&$#%";

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                upperCase++;
            } else if (Character.isLowerCase(ch)) {
                lowerCase++;
            } else if (Character.isDigit(ch)) {
                digit++;
            } else if (invalidChars.contains(String.valueOf(ch))) {
                throw new BadRequestException("Password contains invalid characters");
            }
        }
        
        if (upperCase < 1 ) {
            throw new BadRequestException("Password must contain at least 1 uppercase letter");
        }
        if (lowerCase < 1) {
            throw new BadRequestException("Password must contain at least 1 lowercase letter");
        }
        if (digit < 1) {
            throw new BadRequestException("Password must contain at least 1 digit");
        }
    }
}
