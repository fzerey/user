package com.fzerey.user.shared.exceptions.user;
import com.fzerey.user.shared.exceptions.constants.*;
import com.fzerey.user.shared.exceptions.NotFoundException;
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(ExceptionMessages.USER_NOT_FOUND, ExceptionCodes.USER_NOT_FOUND);
    }
}
