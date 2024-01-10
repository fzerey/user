package com.fzerey.user.infrastructure.io;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
