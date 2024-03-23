package com.fzerey.user.shared.exceptions.user;

import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class UserPasswordGenerationException extends RuntimeException{
    private final String code;
    public UserPasswordGenerationException() {
        super(ExceptionMessages.UNEXPECTED_EXCEPTION);
        this.code = ExceptionCodes.UNEXPECTED_EXCEPTION;
    }
    
    public String getCode() {
        return code;
    }
    
}
