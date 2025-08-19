package com.perfume.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PerfumeStoreWebApp {  // Renamed for clarity

    public static void main(String[] args) {
        SpringApplication.run(PerfumeStoreWebApp.class, args);
    }

    // That's it! No CommandLineRunner, no console dependencies
    // Spring Boot will start the web server automatically
}