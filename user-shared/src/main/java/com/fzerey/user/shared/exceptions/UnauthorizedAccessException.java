package com.fzerey.user.shared.exceptions;

import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class UnauthorizedAccessException extends RuntimeException {
    
    private final String code;

    public UnauthorizedAccessException(){
        super(ExceptionMessages.UNAUTHORIZED_ACCESS_EXCEPTION);
        this.code = ExceptionCodes.UNAUTHORIZED_ACCESS_EXCEPTION;
    }

    public String getCode() {
        return code;
    }
    
    
}
