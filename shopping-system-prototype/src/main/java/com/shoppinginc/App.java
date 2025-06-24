package com.shoppinginc;

import com.shoppinginc.service.CartService;
import com.shoppinginc.service.ICartService;
import com.shoppinginc.ui.TerminalUtils;

public class App {
    public static void main(String[] args) {
        ICartService cartService = new CartService();
        TerminalUtils ui = new TerminalUtils(cartService);
        ui.run();
    }
}