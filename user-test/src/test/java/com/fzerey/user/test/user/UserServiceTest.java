package com.fzerey.user.test.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.Group;
import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.user.UserServiceImpl;
import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;
import com.fzerey.user.shared.exceptions.user.UserAlreadyExistsException;

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

    @Test
    void createUser_whenUserNotExists_shouldCreateUser() {

        CreateUserDto newUserDto = new CreateUserDto("username", "password", "email", "phoneNumber", Long.valueOf(1),
                new ArrayList<UserAttributeDto>()); // Set necessary fields
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(groupRepository.findById(any(Long.class))).thenReturn(Optional.of(new Group()));

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
}
