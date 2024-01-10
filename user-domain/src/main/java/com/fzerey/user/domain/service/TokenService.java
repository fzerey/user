package com.fzerey.user.domain.service;

import com.fzerey.user.domain.model.User;

public interface TokenService {
    public String generateToken(User user);

    public boolean verifyToken(String token);

    public String getUsernameFromToken(String token);

}
