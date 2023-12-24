package com.fzerey.user.service.user;

import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.shared.requests.model.PagedResponse;

public interface UserService {

    public abstract void createUser(CreateUserDto user);

    public abstract void updateUser(UpdateUserDto user);

    public abstract GetUserDto getUser(Long id);

    public abstract PagedResponse<GetUserDto> getUsers(ListUserDto listUserDto);

}
