package com.fzerey.user.shared.exceptions;
public class NotFoundException extends RuntimeException{
    private String code;
    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    
}
