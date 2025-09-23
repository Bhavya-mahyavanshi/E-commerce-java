package src.code.model;

import org.mindrot.jbcrypt.BCrypt;

import src.code.controller.CartItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private Role role;
    private Map<Integer, CartItem> cart = new HashMap<>();

    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.passwordHash = password;
        this.role = role;
    }

    public User() {}

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public Map<Integer, CartItem> getCart() { return cart; }
    public void clearCart() { cart.clear(); }

    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.passwordHash);
    }

    public void addToCart(Product p, int qty) {
        if (cart.containsKey(p.getId())) {
            CartItem item = cart.get(p.getId());
            item.setQuantity(item.getQuantity() + qty);
        } else {
            cart.put(p.getId(), new CartItem(p, qty));
        }
    }

    public double getCartTotal() {
        return cart.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void viewCart() {
    if (cart.isEmpty()) {
        System.out.println("🛒 Cart is empty!");
        return;
    }

    System.out.println("🛒 Your Cart:");
    for (CartItem item : cart.values()) {
        System.out.println(item.getProduct().getName() + " x " + item.getQuantity() +
                           " = $" + item.getTotalPrice());
    }
    System.out.println("Total: $" + getCartTotal());
}

    public Collection<CartItem> getCartItems() {
    return cart.values();
}


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + "', role=" + role + "}";
    }
}
