package com.example;

import com.example.SpringPropsDemoApplication.EmailService;
import com.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringPropsDemoApplication
{
    @Autowired
    public static void main( String[] args ) {
        SpringApplication.run(SpringPropsDemoApplication.class, args);

    @Autowired
    @Qualifier("gmailServce");

    @Autowired
    @Qualifier("backupEmailService")
    private EmailService;

    @Value("${demo.runallservices}")
    private boolean runAllServices;


    }
@Override
    public void run(String... args) throws Exception {
    System.out.println("Email Service Demo Started...");

    System.out.println("Using primary service: ");
    primaryEmailService.send(test Message);
}



}
