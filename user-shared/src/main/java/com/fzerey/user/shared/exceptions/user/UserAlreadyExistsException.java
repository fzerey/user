package com.fzerey.user.shared.exceptions.user;

import com.fzerey.user.shared.exceptions.AlreadyExistsException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class UserAlreadyExistsException extends AlreadyExistsException{

    public UserAlreadyExistsException() {
        super(ExceptionMessages.USER_ALREADY_EXISTS, ExceptionCodes.USER_ALREADY_EXISTS);
    }
    
}
