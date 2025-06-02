package org.example;

public class Book {

    String title;
    String author;
    boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true; // new books available by default
    }

    public void displayStatus() {
        System.out.println("Book: " + title + " by " + author + " (Available: " + isAvailable + ")");
    }

    public void borrowBook() {
        isAvailable = false;
    }
}