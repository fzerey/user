package com.fzerey.user.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.Token;
import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.domain.service.TokenService;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.auth.AuthServiceImpl;
import com.fzerey.user.service.auth.dtos.TokenDto;
import com.fzerey.user.shared.exceptions.UnauthorizedAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        String username = "existingUser";
        String password = "correctPassword";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordService.validatePassword(user, password)).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("token123");

        TokenDto result = authService.login(username, password);

        assertNotNull(result);
        assertEquals("token123", result.getAccessToken());
    }

    @Test
    void testVerify_UserNotFound() {
        String username = "nonExistingUser";
        String newPassword = "newPassword";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UnauthorizedAccessException.class, () -> authService.verify(username, newPassword));
    }

    @Test
    void testVerify_Success() {
        String username = "existingUser";
        String newPassword = "newPassword";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        authService.verify(username, newPassword);

        verify(passwordService).setPassword(user, newPassword);
        verify(user).setVerified(true);
        verify(userRepository).save(user);
    }

    @Test
    void testRefreshToken_TokenInvalid() {
        String token = "invalidToken";
        when(tokenService.verifyToken(token)).thenReturn(false);

        assertThrows(UnauthorizedAccessException.class, () -> authService.refreshToken(token));
    }

    @Test
    void testRefreshToken_TokenNotBelongToUser() {
        String token = "validToken";
        when(tokenService.verifyToken(token)).thenReturn(true);
        when(tokenService.getUsernameFromToken(token)).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedAccessException.class, () -> authService.refreshToken(token));
    }

    @Test
    void testRefreshToken_Success() {
        String token = "validToken";
        String newToken = "newToken";
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        Token tokenObject = new Token(token, user);
        tokenObject.setId(1L);
        user.addToken(tokenObject);
        when(tokenService.verifyToken(token)).thenReturn(true);
        when(tokenService.getUsernameFromToken(token)).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(tokenService.generateToken(user)).thenReturn(newToken);

        TokenDto result = authService.refreshToken(token);

        assertNotNull(result);
        assertEquals(newToken, result.getAccessToken());
    }

    @Test
    void testLogout_UserNotFound() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UnauthorizedAccessException.class, () -> authService.logout(username));
    }

    @Test
    void testLogout_Success() {
        String username = "existingUser";
        User user = mock(User.class);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        authService.logout(username);

        verify(user).logout();
        verify(userRepository).save(user);
    }

}