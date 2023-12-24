package com.fzerey.user.service.user;

import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.model.UserAttribute;
import com.fzerey.user.domain.service.User.PasswordService;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;
import com.fzerey.user.shared.exceptions.user.UserAlreadyExistsException;
import com.fzerey.user.shared.exceptions.user.UserNotFoundException;
import com.fzerey.user.shared.requests.model.PagedResponse;

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
        var existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var group = groupRepository.findById(userDto.getGroupId()).get();
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getPhoneNumber(),
                group);
        passwordService.setPassword(user, userDto.getPassword());
        var attributes = userDto.getAttributes();
        for (var attr : attributes) {
            var attribute = attributeRepository.findByKey(attr.getKey()).get();
            UserAttribute userAttribute = new UserAttribute(user.getId(), attribute.getId(), attr.getValue());
            userAttribute.setUser(user);
            userAttribute.setAttribute(attribute);
            user.addAttribute(userAttribute);
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        var user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
    }

    @Override
    public GetUserDto getUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(),
                user.getUserAttributes().stream()
                        .map(this::convertToUserAttributeDto).collect(Collectors.toSet()));
    }

    @Override
    public PagedResponse<GetUserDto> getUsers(ListUserDto listUserDto) {
        Sort sort = Sort.by(listUserDto.getSortBy());
        if (listUserDto.getSortDirection().equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(listUserDto.getPage() - 1, listUserDto.getSize(), sort);
        var users = listUserDto.getQuery() != null && listUserDto.getGroupId() != null ? userRepository
                .findByGroupIdAndSearchByMultipleFields(pageable, listUserDto.getGroupId(), listUserDto.getQuery())
                : listUserDto.getQuery() != null ? userRepository.findByQuery(listUserDto.getQuery(),
                        pageable)
                        : listUserDto.getGroupId() != null
                                ? userRepository.findByGroupId(pageable, listUserDto.getGroupId())
                                : userRepository.findAll(pageable);
        var response = new PagedResponse<GetUserDto>();
        response.fromPage(users.map(this::convertToGetUserDto));
        return response;
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
