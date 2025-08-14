package com.perfume.capstone;

import com.perfume.capstone.view.Inventory;
import com.perfume.capstone.view.Kiosk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagerApp implements CommandLineRunner {

    @Autowired
    private Kiosk kiosk;

    @Autowired
    private Inventory inventory;

    @Value("${candlestore.mode:kiosk}")
    private String mode;

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (mode.equalsIgnoreCase("admin")) {
            inventory.run();
        } else {
            kiosk.run();
        }
    }
}