package com.example.service;

import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class GmailService implements EmailService {
    // smtp simple mail transfer protocol

    @Value("${email.gmail.enabled:true}")
    public boolean enabled;

    @Value("${email.gmail.server:smtp.gmail.com}")
    private String server;

    @Value("${email.gmail.port.port:587}")
    private int port;

    @Override
    public void send(String message) {
        if (!enabled) {
            System.out.println("Tried to send mail but it's disabled.");
            return;
        }
        System.out.printf("Gmail send: %s%n", message);
    }
}

    // IP Address
    // house number
    // 137.56.23.234: prt# 0 to 65535, 0 - 1000 (reserved), 80 HTTP, 443 HTTPS, FTP 21, SSH (22)
    // 1000 - 50000 = general applications
    // 50000 + temporary
}
