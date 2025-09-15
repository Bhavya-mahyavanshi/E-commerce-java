package controller;

import java.util.HashMap;
import java.util.Map;

public class CartService {
    private Map<String, Integer> items;

    public CartService() {
        items = new HashMap<>();
    }

    public void addItem(String name, int quantity) {
        items.put(name, items.getOrDefault(name, 0) + quantity);
    }

    public void removeItem(String name) {
        items.remove(name);
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
