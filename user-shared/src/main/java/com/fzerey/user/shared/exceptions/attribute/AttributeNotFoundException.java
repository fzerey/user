package com.fzerey.user.shared.exceptions.attribute;

import com.fzerey.user.shared.exceptions.NotFoundException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class AttributeNotFoundException extends NotFoundException {

    public AttributeNotFoundException(String attributeKey) {
        super(String.format("%s %s", ExceptionMessages.ATTRIBUTE_NOT_FOUND, attributeKey), ExceptionCodes.ATTRIBUTE_NOT_FOUND);
    }

}
