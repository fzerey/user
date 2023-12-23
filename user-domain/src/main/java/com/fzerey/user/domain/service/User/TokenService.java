package com.fzerey.user.domain.service.User;

import com.fzerey.user.domain.model.User;

public interface TokenService {
    public String generateToken(User user);
}
