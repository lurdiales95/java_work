
package com.example.bookstore;

import com.example.bookstore.view.Inventory;
import com.example.bookstore.view.Kiosk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private Kiosk kiosk;

	@Autowired
	private Inventory inventory;

	@Value("${bookstore.mode:kiosk}")
	private String mode;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
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
