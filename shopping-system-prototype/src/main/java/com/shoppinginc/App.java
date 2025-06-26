package com.shoppinginc;

// imports from classes: CartService, ICartService, and Terminal Utils
import com.shoppinginc.service.CartService;
import com.shoppinginc.service.ICartService;
import com.shoppinginc.ui.TerminalUtils;

public class App {
    public static void main(String[] args) {
        // brings in class that provides CartService rules
        ICartService cartService = new CartService();

        // brings in the class that provides details on user interface
        // identifies TerminalUtils as in ui folder
        TerminalUtils ui = new TerminalUtils(cartService);

        // app activation which allows communication between ui and app
        ui.run();
    }
}