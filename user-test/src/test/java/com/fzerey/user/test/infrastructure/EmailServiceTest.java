package com.fzerey.user.test.infrastructure;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.fzerey.user.infrastructure.io.EmailServiceImpl;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailServiceImpl(emailSender);
    }

    @Test
    void sendSimpleMessage_ShouldSendEmail() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test message body";

        emailService.sendSimpleMessage(to, subject, text);

        // Verify if a mail is sent with the correct attributes
        verify(emailSender, times(1)).send(argThat((SimpleMailMessage message) -> {
            return message.getTo()[0].equals(to) && message.getSubject().equals(subject)
                    && message.getText().equals(text);
        }));
    }
}
