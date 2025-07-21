package com.example.bookstore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItem {
    private final Book book;
    private int quantity;
    private final BigDecimal price;
    private BigDecimal extendedPrice;

    public CartItem(Book book, int quantity, BigDecimal price) {
        this.book = book;
        this.quantity = quantity;
        this.price = price;
        this.extendedPrice = price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
        this.extendedPrice = this.price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    @Override
    public String toString() {
        return String.format("%s - Qty: %d - Price: $%s - Extended: $%s",
                book.toString(), quantity, price, extendedPrice);
    }
}
