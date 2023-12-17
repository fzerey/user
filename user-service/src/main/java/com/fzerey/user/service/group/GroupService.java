package com.fzerey.user.service.group;

import java.util.List;

import com.fzerey.user.service.group.dtos.*;

public interface GroupService {
    

    public abstract void createGroup(CreateGroupDto groupDto);
    public abstract void updateGroup(UpdateGroupDto groupDto);
    public abstract void deleteGroup(Long id);
    public abstract GetGroupDto getGroup(Long id);
    public abstract List<GetGroupDto> getGroups();


}
