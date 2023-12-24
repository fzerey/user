package com.fzerey.user.shared.exceptions;
public class AlreadyExistsException extends RuntimeException {
    private final String code;
    public AlreadyExistsException(String message, String code) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
