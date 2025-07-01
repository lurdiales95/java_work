package com.example.config;

import com.example.SpringPropsDemoApplication.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Value("{email.backup.enabled:false}")
    private boolean backupEnabled;

    @Bean("backupEmailService")
    public EmailService backupEmailService() {
        if (!backupEnabled) {
            return newEmailsService () {
                @Override
                public void send(String message) {
                    System.out.println("Backup email service is disabled.");
                }
            };
        }

        return new EmailService() {
        @Override
                public void send(String message) {
            System.out.println("Backup Email: " + message);
        }

    } private EmailConfig() {
    }
}
