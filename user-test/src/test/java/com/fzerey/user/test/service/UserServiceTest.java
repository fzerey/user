package com.fzerey.user.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fzerey.user.domain.model.Attribute;
import com.fzerey.user.domain.model.Group;
import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.user.UserServiceImpl;
import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.GetUserDto;
import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.service.user.dtos.UpdateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;
import com.fzerey.user.shared.exceptions.attribute.AttributeNotFoundException;
import com.fzerey.user.shared.exceptions.group.GroupNotFoundException;
import com.fzerey.user.shared.exceptions.user.UserAlreadyExistsException;
import com.fzerey.user.shared.exceptions.user.UserNotFoundException;
import com.fzerey.user.shared.requests.model.PagedResponse;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private AttributeRepository attributeRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Page<User> createMockedPage() {
        List<User> list = Arrays.asList(new User(), new User()); // Replace with actual user creation logic
        return new PageImpl<>(list);
    }

    @Test
    void createUser_whenUserNotExists_shouldCreateUser() {

        var attributeList = new ArrayList<UserAttributeDto>();
        attributeList.add(new UserAttributeDto("key", "value"));

        CreateUserDto newUserDto = new CreateUserDto("username", "password", "email", "phoneNumber", Long.valueOf(1),
                attributeList); // Set necessary fields
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group()));
        var attribute = new Attribute();
        attribute.setKey("key");
        attribute.setId(Long.valueOf(1));
        when(attributeRepository.findByKey(anyString())).thenReturn(Optional.of(attribute));

        userService.createUser(newUserDto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_whenUserExists_shouldThrowException() {

        CreateUserDto newUserDto = new CreateUserDto("username", "password", "email", "phoneNumber", Long.valueOf(1),
                new ArrayList<UserAttributeDto>()); // Set necessary fields
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(newUserDto));
    }

    @Test
    void createUser_whenGroupNotExists_shouldThrowException() {

        CreateUserDto newUserDto = new CreateUserDto("username", "password", "email", "phoneNumber", Long.valueOf(1),
                new ArrayList<UserAttributeDto>()); // Set necessary fields
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> userService.createUser(newUserDto));
    }

    @Test
    void createUser_whenAttributeNotExists_shouldThrowException() {
        var attributeList = new ArrayList<UserAttributeDto>();
        attributeList.add(new UserAttributeDto("key", "value"));
        CreateUserDto newUserDto = new CreateUserDto("username", "password", "email", "phoneNumber", Long.valueOf(1),
                attributeList); // Set necessary fields
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group()));
        when(attributeRepository.findByKey(anyString())).thenReturn(Optional.empty());

        assertThrows(AttributeNotFoundException.class, () -> userService.createUser(newUserDto));
    }

    @Test
    void updateUser_whenUserExists_shouldUpdateUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));

        userService.updateUser(new com.fzerey.user.service.user.dtos.UpdateUserDto(Long.valueOf(1), "username", null));

        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_whenUserNotExists_shouldThrowException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        UpdateUserDto updateUserDto = new UpdateUserDto(Long.valueOf(1), "username", null);
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(updateUserDto));
    }

    @Test
    void getUsers_WithDefaultSettings_ShouldReturnAllUsers() {
        ListUserDto listUserDto = new ListUserDto();
        listUserDto.setPage(1);
        listUserDto.setSize(10);
        listUserDto.setSortBy("username");
        listUserDto.setSortDirection("asc");

        when(userRepository.findAll(any(Pageable.class))).thenReturn(createMockedPage());

        PagedResponse<GetUserDto> result = userService.getUsers(listUserDto);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(userRepository).findAll(any(Pageable.class));
    }

    @Test
    void getUsers_WithGroupId_ShouldReturnFilteredUsers() {
        ListUserDto listUserDto = new ListUserDto();
        listUserDto.setPage(1);
        listUserDto.setSize(10);
        listUserDto.setSortBy("username");
        listUserDto.setSortDirection("asc");
        listUserDto.setGroupId(1L);

        when(userRepository.findByGroupId(any(Pageable.class), anyLong())).thenReturn(createMockedPage());

        PagedResponse<GetUserDto> result = userService.getUsers(listUserDto);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(userRepository).findByGroupId(any(Pageable.class), eq(1L));
    }

    @Test
    void getUsers_WithQuery_ShouldReturnFilteredUsers() {
        ListUserDto listUserDto = new ListUserDto();
        listUserDto.setPage(1);
        listUserDto.setSize(10);
        listUserDto.setSortBy("username");
        listUserDto.setSortDirection("asc");
        listUserDto.setQuery("searchTerm");

        when(userRepository.findByQuery(anyString(), any(Pageable.class))).thenReturn(createMockedPage());

        PagedResponse<GetUserDto> result = userService.getUsers(listUserDto);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(userRepository).findByQuery(eq("searchTerm"), any(Pageable.class));
    }
}
