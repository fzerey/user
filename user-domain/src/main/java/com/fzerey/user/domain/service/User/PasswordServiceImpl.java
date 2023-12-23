package com.fzerey.user.domain.service.User;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public void setPassword(User user, String password) {
        byte[] salt = generateSalt();
        String hashedPassword = get_SHA_256_SecurePassword(password, salt);
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);
    }

    @Override
    public boolean validatePassword(User user, String inputPassword) {
        String newHashedPassword = get_SHA_256_SecurePassword(inputPassword, user.getSalt());
        return newHashedPassword.equals(user.getHashedPassword());
    }

    private String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

}