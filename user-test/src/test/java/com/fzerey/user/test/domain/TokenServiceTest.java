package com.fzerey.user.test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.TokenServiceImpl;

class TokenServiceTest {

    @Mock
    private User user;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService.setSecretKey("testSecretKey");
    }

    @Test
    void generateToken_ValidUser_ReturnsToken() {
        when(user.getUsername()).thenReturn("testUser");
        when(user.getSubId()).thenReturn("1234");

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void verifyToken_ValidToken_ReturnsTrue() {
        when(user.getUsername()).thenReturn("testUser");
        when(user.getSubId()).thenReturn("123L");
        String token = tokenService.generateToken(user);

        assertTrue(tokenService.verifyToken(token));
    }

    @Test
    void verifyToken_InvalidToken_ReturnsFalse() {
        String invalidToken = "invalidToken";

        assertFalse(tokenService.verifyToken(invalidToken));
    }

    @Test
    void getUsernameFromToken_ValidToken_ReturnsUsername() {
        String username = "testUser";
        when(user.getUsername()).thenReturn(username);
        when(user.getSubId()).thenReturn("testUser");
        String token = tokenService.generateToken(user);

        assertEquals(username, tokenService.getUsernameFromToken(token));
    }

}
