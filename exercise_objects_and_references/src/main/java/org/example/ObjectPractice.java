package org.example;

import java.sql.SQLOutput;

public class ObjectPractice {

    public static void main(String[] args) {

        Car car1 = new Car(   "\nToyota", "Corolla", 2020);
        Car car2 = new Car("Ford", "Mustang",  2022 );
        car1.displayInfo();
        car2.displayInfo();

        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien");
        book1.displayStatus();
        System.out.println("Borrowing the book...");
        book1.borrowBook();
        book1.displayStatus();


        BankAccount acc1 = new BankAccount("Alice", 1000.0);
        BankAccount acc2 = acc1; // acc2 is equal to acc1
        acc1.displayBalance();
        System.out.println("Depositing $500 to acc2...");
        acc2.deposit(500);
        acc1.displayBalance();

        new Employee("John");
        new Employee("Jane");
        new Employee("Mike");
        System.out.println("\nTotal Employees: " + Employee.getTotalEmployees());
    }
}
