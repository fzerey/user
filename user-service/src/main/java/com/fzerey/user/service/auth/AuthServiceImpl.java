package com.fzerey.user.service.auth;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.service.User.PasswordService;
import com.fzerey.user.domain.service.User.TokenService;
import com.fzerey.user.infrastructure.repository.UserRepository;
import com.fzerey.user.service.auth.dtos.TokenDto;

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
        var user = userRepository.findByUsername(username);
        boolean isValid = passwordService.validatePassword(user, password);
        if(!isValid) {
            throw new RuntimeException("Invalid username or password");
        }
        return new TokenDto(tokenService.generateToken(user));
    }

    @Override
    public void logout(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public boolean validateToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

}
