package com.fzerey.user.shared.exceptions.group;

import com.fzerey.user.shared.exceptions.NotFoundException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

public class GroupNotFoundException extends NotFoundException{

    public GroupNotFoundException() {
        super(ExceptionMessages.GROUP_NOT_FOUND, ExceptionCodes.GROUP_NOT_FOUND);
    }
}
