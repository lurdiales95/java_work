package com.example.service;

import org.springframework.beans.factory.annotation.Value;

public class OutlookService implements EmailService{

    @Value("${email.outlook.enabled:true}")
    public boolean enabled;

    @Value("${email.outlook.server:smtp.gmail.com}")
    private String server;

    @Value("${email.outlook.port.port:587}")
    private int port;

    @Override
    public void send(String message) {
        if (!enabled) {
            System.out.println("Tried to send mail but it's disabled.");
            return;
        }
        System.out.printf("Outlook send: %s%n", message);
    }
}