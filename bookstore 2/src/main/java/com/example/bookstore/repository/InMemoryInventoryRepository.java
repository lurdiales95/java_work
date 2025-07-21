package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.InventoryItem;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<String, InventoryItem> inventory = new HashMap<>();

    public InMemoryInventoryRepository() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Fantasy Books
        addSampleBook("0001", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 15, "12.99");
        addSampleBook("0002", "The Fellowship of the Ring", "J.R.R. Tolkien", "Fantasy", 8, "14.99");
        addSampleBook("0003", "A Game of Thrones", "George R.R. Martin", "Fantasy", 12, "16.99");
        addSampleBook("0004", "The Way of Kings", "Brandon Sanderson", "Fantasy", 6, "18.99");
        addSampleBook("0005", "The Name of the Wind", "Patrick Rothfuss", "Fantasy", 10, "15.99");

        // Science Fiction Books
        addSampleBook("0006", "Dune", "Frank Herbert", "Science Fiction", 9, "17.99");
        addSampleBook("0007", "Foundation", "Isaac Asimov", "Science Fiction", 7, "13.99");
        addSampleBook("0008", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 14, "12.99");
        addSampleBook("0009", "Neuromancer", "William Gibson", "Science Fiction", 5, "15.99");
        addSampleBook("0010", "Ender's Game", "Orson Scott Card", "Science Fiction", 11, "14.99");
        addSampleBook("0011", "The Martian", "Andy Weir", "Science Fiction", 13, "16.99");

        // Programming Books
        addSampleBook("0012", "Effective Java", "Joshua Bloch", "Programming", 4, "54.99");
        addSampleBook("0013", "Clean Code", "Robert C. Martin", "Programming", 6, "49.99");
        addSampleBook("0014", "Effective C++", "Scott Meyers", "Programming", 3, "52.99");
        addSampleBook("0015", "The Pragmatic Programmer", "David Thomas & Andrew Hunt", "Programming", 5, "47.99");
        addSampleBook("0016", "Head First Design Patterns", "Eric Freeman", "Programming", 7, "44.99");
        addSampleBook("0017", "Java: The Complete Reference", "Herbert Schildt", "Programming", 8, "59.99");
        addSampleBook("0018", "Learning Python", "Mark Lutz", "Programming", 4, "64.99");
        addSampleBook("0019", "Introduction to Algorithms", "Thomas H. Cormen", "Programming", 2, "89.99");
    }

    private void addSampleBook(String isbn, String title, String author, String genre, int quantity, String price) {
        Book book = new Book(isbn, title, author, genre);
        InventoryItem item = new InventoryItem(book, quantity, new BigDecimal(price));
        inventory.put(isbn, item);
    }

    @Override
    public List<InventoryItem> getAll() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public List<InventoryItem> getInStock() {
        return inventory.values().stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void add(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        inventory.put(item.getBook().isbn(), item);
    }

    @Override
    public void update(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        String isbn = item.getBook().isbn();
        if (!inventory.containsKey(isbn)) {
            throw new IllegalArgumentException("Item with ISBN " + isbn + " not found");
        }
        inventory.put(isbn, item);
    }

    @Override
    public void delete(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        inventory.remove(isbn);
    }

    @Override
    public InventoryItem getByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        return inventory.get(isbn);
    }
}

