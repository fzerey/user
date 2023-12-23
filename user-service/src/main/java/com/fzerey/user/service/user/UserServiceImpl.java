package com.fzerey.user.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.model.UserAttribute;
import com.fzerey.user.domain.service.User.PasswordService;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AttributeRepository attributeRepository;
    private final PasswordService passwordService;

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
            AttributeRepository attributeRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.attributeRepository = attributeRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void createUser(CreateUserDto userDto) {
        var group = groupRepository.findById(userDto.getGroupId()).get();
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getPhoneNumber(), group);
        passwordService.setPassword(user, userDto.getPassword());
        var attributes = userDto.getAttributes();
        for (var attr : attributes) {
            var attribute = attributeRepository.findByKey(attr.getKey()).get();
            UserAttribute userAttribute = new UserAttribute();
            userAttribute.setUser(user);
            userAttribute.setAttribute(attribute);
            userAttribute.setValue(attr.getValue());
            user.addAttribute(userAttribute);
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        var user = userRepository.findById(userDto.getId()).get();
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
    }

    @Override
    public GetUserDto getUser(Long id) {
        var user = userRepository.findById(id).get();
        return new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(),
                user.getUserAttributes().stream()
                        .map(this::convertToUserAttributeDto).collect(Collectors.toSet()));
    }

    @Override
    public List<GetUserDto> getUsers() {
        return userRepository.findAll().stream().map(this::convertToGetUserDto).collect(Collectors.toList());
    }

    private GetUserDto convertToGetUserDto(User user) {
        GetUserDto dto = new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(),
                user.getUserAttributes().stream()
                        .map(this::convertToUserAttributeDto).collect(Collectors.toSet()));
        return dto;
    }

    private UserAttributeDto convertToUserAttributeDto(UserAttribute userAttribute) {
        UserAttributeDto dto = new UserAttributeDto(userAttribute.getAttribute().getKey(), userAttribute.getValue());
        return dto;
    }

}
