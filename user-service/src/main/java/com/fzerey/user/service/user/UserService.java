package com.fzerey.user.service.user;

import com.fzerey.user.service.user.dtos.SignupUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.shared.requests.model.PagedResponse;

public interface UserService {

    public abstract void signup(SignupUserDto user);

    public abstract void updateUser(UpdateUserDto user);

    public abstract GetUserDto getUser(Long id);

    public abstract PagedResponse<GetUserDto> getUsers(ListUserDto listUserDto);

    public abstract void verify(Long id, String code);

}
