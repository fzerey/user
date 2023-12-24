package com.fzerey.user.service.group;

import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.ListGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;
import com.fzerey.user.shared.requests.model.PagedResponse;

public interface GroupService {
    

    public abstract void createGroup(CreateGroupDto groupDto);
    public abstract void updateGroup(UpdateGroupDto groupDto);
    public abstract void deleteGroup(Long id);
    public abstract GetGroupDto getGroup(Long id);
    public abstract PagedResponse<GetGroupDto> getGroups(ListGroupDto listGroupDto);


}
