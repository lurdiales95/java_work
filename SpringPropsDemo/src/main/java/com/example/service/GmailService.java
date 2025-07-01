package com.example.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class GmailService {
    // smtp simple mail transfer protocol


    @Value("${email.gmail.server:smtp.gmail.com}")
    private String server;

    @Value("${email.gmail.port.port:587}")
    private int port;

    // IP Address
    // house number
    // 137.56.23.234: prt# 0 to 65535, 0 - 1000 (reserved), 80 HTTP, 443 HTTPS, FTP 21, SSH (22)
    // 1000 - 50000 = general applications
    // 50000 + temporary
}
