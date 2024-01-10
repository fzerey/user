package com.fzerey.user.test.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordServiceImpl;

class PasswordServiceTest {

    private PasswordServiceImpl passwordService;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordService = new PasswordServiceImpl();
    }

    @Test
    void setPassword_ShouldSetSaltAndHashedPassword() {
        String password = "password";
        passwordService.setPassword(user, password);

        verify(user).setSalt(any(byte[].class));
        verify(user).setHashedPassword(any(String.class));
    }

    @Test
    void validatePassword_CorrectPassword_ReturnsTrue() {
        String password = "password";
        User user = new User();
        passwordService.setPassword(user, password);

        boolean isValid = passwordService.validatePassword(user, password);

        assertTrue(isValid);
    }

    @Test
    void validatePassword_IncorrectPassword_ReturnsFalse() {
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        User user = new User();
        passwordService.setPassword(user, correctPassword);

        boolean isValid = passwordService.validatePassword(user, wrongPassword);

        assertFalse(isValid);
    }

}
