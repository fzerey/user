package com.fzerey.user.service.auth;

import org.springframework.stereotype.Service;

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
    public TokenDto login(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(UnauthorizedAccessException::new);
        boolean isValid = passwordService.validatePassword(user, password);
        if(!isValid) {
            throw new UnauthorizedAccessException();
        }
        return new TokenDto(tokenService.generateToken(user));
    }

}
