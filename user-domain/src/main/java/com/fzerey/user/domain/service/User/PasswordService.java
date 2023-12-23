package com.fzerey.user.domain.service.User;

import com.fzerey.user.domain.model.User;

public interface PasswordService {    
    public void setPassword(User user, String password);
    public boolean validatePassword(User user, String inputPassword);

}
