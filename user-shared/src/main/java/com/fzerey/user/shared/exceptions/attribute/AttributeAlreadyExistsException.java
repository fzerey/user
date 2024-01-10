package com.fzerey.user.shared.exceptions.attribute;

import com.fzerey.user.shared.exceptions.AlreadyExistsException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class AttributeAlreadyExistsException extends AlreadyExistsException{

    public AttributeAlreadyExistsException() {
        super(ExceptionMessages.ATTRIBUTE_ALREADY_EXISTS, ExceptionCodes.ATTRIBUTE_ALTREADY_EXISTS);
    }
    
}
