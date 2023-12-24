package com.fzerey.user.shared.exceptions.attribute;

import com.fzerey.user.shared.exceptions.NotFoundException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class AttributeNotFoundException extends NotFoundException {

    public AttributeNotFoundException() {
        super(ExceptionMessages.ATTRIBUTE_NOT_FOUND, ExceptionCodes.ATTRIBUTE_NOT_FOUND);
    }

}
