package com.fzerey.user.service.auth;

import com.fzerey.user.service.auth.dtos.TokenDto;

public interface AuthService {
    public TokenDto login(String username, String password);
    public void logout(String token);
    public boolean validateToken(String token);
}
