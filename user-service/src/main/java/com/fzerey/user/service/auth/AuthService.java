package com.fzerey.user.service.auth;

import com.fzerey.user.service.auth.dtos.TokenDto;

public interface AuthService {
    public TokenDto login(String username, String password);

    public void logout(String username);

    public TokenDto refreshToken(String token);

    public void verify(String username, String code);

}
