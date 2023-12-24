package com.fzerey.user.shared.exceptions.constants;

public class ExceptionCodes {
    private ExceptionCodes() {
    }

    public static final String USER_NOT_FOUND = "101";
    public static final String USER_ALREADY_EXISTS = "102";
    public static final String GROUP_NOT_FOUND = "201";
    public static final String GROUP_ALREADY_EXISTS = "202";    
    public static final String ATTRIBUTE_NOT_FOUND = "301";
    public static final String UNAUTHORIZED_ACCESS_EXCEPTION = "401";
    public static final String UNEXPECTED_EXCEPTION = "500";
}
