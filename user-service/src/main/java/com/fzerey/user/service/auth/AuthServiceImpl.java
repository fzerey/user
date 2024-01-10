package com.fzerey.user.service.auth;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Token;
import com.fzerey.user.domain.model.User;
import com.fzerey.user.domain.service.PasswordService;
import com.fzerey.user.domain.service.TokenService;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.auth.dtos.TokenDto;
import com.fzerey.user.shared.exceptions.UnauthorizedAccessException;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordService passwordService, TokenService tokenService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public TokenDto login(String username, String password) throws UnauthorizedAccessException {
        var user = userRepository.findByUsername(username).orElseThrow(UnauthorizedAccessException::new);
        boolean isValid = passwordService.validatePassword(user, password);
        if (!isValid) {
            throw new UnauthorizedAccessException();
        }
        var tokenDto = new TokenDto(tokenService.generateToken(user));
        var token = new Token(tokenDto.getAccessToken(), user);
        user.getTokens().add(token);
        userRepository.save(user);
        return tokenDto;
    }

    @Override
    public void verify(String username, String newPassword) {
        var user = userRepository.findByUsername(username).orElseThrow(UnauthorizedAccessException::new);
        passwordService.setPassword(user, newPassword);
        user.setVerified(true);
        userRepository.save(user);
    }

    @Override
    public TokenDto refreshToken(String token) {
        boolean isTokenValid = tokenService.verifyToken(token);
        if (isTokenValid) {
            User user = userRepository.findByUsername(tokenService.getUsernameFromToken(token))
                    .orElseThrow(UnauthorizedAccessException::new);
            var existingToken = user.getTokens().stream().filter(t -> t.getAccessToken().equals(token)).findFirst()
                    .orElseThrow(UnauthorizedAccessException::new);
            var refreshToken = new TokenDto(tokenService.generateToken(user));
            user.refreshToken(existingToken, new Token(refreshToken.getAccessToken(), user));
        }
        throw new UnauthorizedAccessException();

    }

    @Override
    public void logout(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(UnauthorizedAccessException::new);
        user.logout();
    }

}
