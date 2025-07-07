package com.example.config;

import com.example.SpringPropsDemoApplication.EmailService;
import com.example.SpringPropsDemo.service.OutlookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("{email.backup.enabled:false}")
    private boolean backupEnabled;

    @Bean("backupEmailService")
    public EmailService backupEmailService() {
        //We could implement complex logic here to decide which service to create or configure.

        if (!backupEnabled) {
            return new EmailsService() {
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
        };
    }
}

