package com.example.service;

.*;


import org.springframework.beans.factory.annotation.Value;

public class OutlookService {

    // smtp simple mail transfer protocol


    @Value("${email.gmail.server:smtp.gmail.com}")
    private String server;

    @Value("${email.gmail.port.port:587}")
    private int port;

}
