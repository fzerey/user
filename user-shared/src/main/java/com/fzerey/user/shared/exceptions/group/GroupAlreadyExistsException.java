package com.fzerey.user.shared.exceptions.group;

import com.fzerey.user.shared.exceptions.AlreadyExistsException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class GroupAlreadyExistsException extends AlreadyExistsException{

    public GroupAlreadyExistsException() {
        super(ExceptionMessages.GROUP_ALREADY_EXISTS, ExceptionCodes.GROUP_ALREADY_EXISTS);
    }
    
}
