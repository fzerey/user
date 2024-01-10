package com.fzerey.user.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.domain.service.TokenService;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.auth.AuthServiceImpl;
import com.fzerey.user.service.auth.dtos.TokenDto;
import com.fzerey.user.shared.exceptions.UnauthorizedAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;


class AuthServiceTest {

    @Mock
    private PasswordService passwordService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(passwordService, tokenService, userRepository);
    }

    @Test
    void testLogin_InvalidUser() {
        String username = "nonExistingUser";
        String password = "password";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UnauthorizedAccessException.class, () -> authService.login(username, password));

    }

    @Test
    void testLogin_InvalidPassword() {
        String username = "existingUser";
        String password = "wrongPassword";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordService.validatePassword(user, password)).thenReturn(false);

        assertThrows(UnauthorizedAccessException.class, () -> authService.login(username, password));
    }

    @Test
    void testLogin_Success() {
        // Given
        String username = "existingUser";
        String password = "correctPassword";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordService.validatePassword(user, password)).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("token123");

        // When
        TokenDto result = authService.login(username, password);

        // Then
        assertNotNull(result);
        assertEquals("token123", result.getAccessToken());
    }
}