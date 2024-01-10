package com.fzerey.user.service.user;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.model.UserAttribute;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.infrastructure.io.EmailService;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.service.user.dtos.SignupUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;
import com.fzerey.user.shared.exceptions.attribute.AttributeNotFoundException;
import com.fzerey.user.shared.exceptions.group.GroupNotFoundException;
import com.fzerey.user.shared.exceptions.user.UserAlreadyExistsException;
import com.fzerey.user.shared.exceptions.user.UserNotFoundException;
import com.fzerey.user.shared.requests.model.PagedResponse;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AttributeRepository attributeRepository;
    private final PasswordService passwordService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
            AttributeRepository attributeRepository, PasswordService passwordService, EmailService emailService) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.attributeRepository = attributeRepository;
        this.passwordService = passwordService;
        this.emailService = emailService;
    }

    @Override
    public void signup(SignupUserDto userDto) {
        userRepository.findByUsername(userDto.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException();
        });
        var group = groupRepository.findById(userDto.getGroupId()).orElseThrow(GroupNotFoundException::new);
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), group);
        passwordService.setPassword(user, userDto.getPassword());
        var attributes = userDto.getAttributes();
        for (var attr : attributes) {
            var attribute = attributeRepository.findByKey(attr.getKey())
                    .orElseThrow(() -> new AttributeNotFoundException(attr.getKey()));
            UserAttribute userAttribute = new UserAttribute(user.getId(), attribute.getId(), attr.getValue());
            userAttribute.setUser(user);
            userAttribute.setAttribute(attribute);
            user.addAttribute(userAttribute);
        }
        passwordService.generateVerificationCode(user);
        userRepository.save(user);
        emailService.sendSimpleMessage(user.getEmail(), "Verification Code",
                String.format("Hello %s, your verification code is %s",
                        user.getUsername(), user.getVerificationCode()));

    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        var user = userRepository.findById(userDto.getId()).orElseThrow(UserNotFoundException::new);
        if (userDto.getAttributes() != null) {
            for (var attr : userDto.getAttributes()) {
                var attribute = attributeRepository.findByKey(attr.getKey())
                        .orElseThrow(() -> new AttributeNotFoundException(attr.getKey()));
                UserAttribute userAttribute = new UserAttribute(userDto.getId(), attribute.getId(), attr.getValue());
                userAttribute.setUser(user);
                userAttribute.setAttribute(attribute);
                user.addAttribute(userAttribute);
            }
        }
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
    }

    @Override
    public GetUserDto getUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
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
        Page<User> pagedResult;
        if (listUserDto.getQuery() != null && listUserDto.getGroupId() != null) {
            pagedResult = userRepository.findByGroupIdAndSearchByMultipleFields(pageable, listUserDto.getGroupId(),
                    listUserDto.getQuery());
        } else if (listUserDto.getQuery() != null) {
            pagedResult = userRepository.findByQuery(listUserDto.getQuery(), pageable);
        } else if (listUserDto.getGroupId() != null) {
            pagedResult = userRepository.findByGroupId(pageable, listUserDto.getGroupId());
        } else {
            pagedResult = userRepository.findAll(pageable);
        }

        var users = pagedResult;
        var response = new PagedResponse<GetUserDto>();
        response.fromPage(users.map(this::convertToGetUserDto));
        return response;
    }

    @Override
    public void verify(Long id, String code) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setVerified(true);
        user.setVerificationCode(null);
        userRepository.save(user);
    }

    private GetUserDto convertToGetUserDto(User user) {
        return new GetUserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(),
                user.getUserAttributes().stream()
                        .map(this::convertToUserAttributeDto).collect(Collectors.toSet()));
    }

    private UserAttributeDto convertToUserAttributeDto(UserAttribute userAttribute) {
        return new UserAttributeDto(userAttribute.getAttribute().getKey(), userAttribute.getValue());
    }

}
