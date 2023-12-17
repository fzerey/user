package com.fzerey.user.service.user;

import java.util.List;

import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;

public interface UserService {

    public abstract void createUser(CreateUserDto user);

    public abstract void updateUser(UpdateUserDto user);

    public abstract GetUserDto getUser(Long id);

    public abstract List<GetUserDto> getUsers();

}
