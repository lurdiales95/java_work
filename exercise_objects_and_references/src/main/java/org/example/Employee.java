package org.example;

public class Employee {
    String name;
    static int totalEmployees = 0;

    public Employee(String name) {

        this.name = name;
        totalEmployees++;


    }

    public String getName() {
        return name;
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }
}
