package com.fzerey.user.shared.exceptions.token;

import com.fzerey.user.shared.exceptions.UnauthorizedAccessException;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class InvalidTokenException extends UnauthorizedAccessException {
    public InvalidTokenException() {
        super(ExceptionMessages.INVALID_TOKEN_EXCEPTION, ExceptionMessages.INVALID_TOKEN_EXCEPTION);
    }

}
