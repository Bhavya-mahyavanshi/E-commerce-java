package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Product> cart;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.cart = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<Product> getCart() { return cart; }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void clearCart() {
        cart.clear();
    }
}
