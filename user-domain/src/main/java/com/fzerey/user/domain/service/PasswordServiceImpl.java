package com.fzerey.user.domain.service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.User;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public void setPassword(User user, String password) {
        byte[] salt = generateSalt();
        String hashedPassword = getSha256SecurePassword(password, salt);
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);
    }

    @Override
    public boolean validatePassword(User user, String inputPassword) {
        String newHashedPassword = getSha256SecurePassword(inputPassword, user.getSalt());
        return newHashedPassword != null && newHashedPassword.equals(user.getHashedPassword());
    }

    private String getSha256SecurePassword(String passwordToHash, byte[] salt) {
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

    private static final Random random = new Random();

    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    @Override
    public void generateVerificationCode(User user) {
        String code = String.format("%06d", random.nextInt(999999));
        user.setVerificationCode(code);
    }

}
