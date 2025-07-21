package com.example.bookstore.model;

public record Book(String isbn, String title, String author, String genre) {

    @Override
    public String toString() {
        return String.format("'%s' by %s [%s]", title, author, genre);
    }
}
