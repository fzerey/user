package com.fzerey.user.shared.util;

import java.security.SecureRandom;

public final class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";

    private static final SecureRandom secureRandom = new SecureRandom();

    private PasswordGenerator() {
    }

    public static String generate(int length, boolean useLower, boolean useUpper, boolean useDigits, boolean usePunctuation){
        StringBuilder password = new StringBuilder(length);

        String charCategories = (useLower ? LOWER : "") + (useUpper ? UPPER : "") + (useDigits ? DIGITS : "") + (usePunctuation ? PUNCTUATION : "");

        for(int i = 0; i < length; i++){
            password.append(charCategories.charAt(secureRandom.nextInt(charCategories.length())));
        }

        return new String(password);
    }

    public static String generate(){
        return generate(8, true, true, true, true);
    }
    
}
