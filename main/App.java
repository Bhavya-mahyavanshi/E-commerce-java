package main;

import models.Product;
import services.CartServices;

public class App {
    public static void main(String[] args) {
        Product p1 = new Product(1, "Laptop", 1000, 5);
        Product p2 = new Product(2, "Mouse", 20, 5);

        CartServices cart = new CartServices();

        cart.addToCart(p1, 1);
        cart.addToCart(p1, 1);
        cart.addToCart(p2, 10);

        cart.viewCart();
        System.out.println("Toatl: $" + cart.getTotal());
    }
}
