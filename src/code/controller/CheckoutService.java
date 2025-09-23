package src.code.controller;

import src.code.model.InventoryService;
import src.code.model.Product;
import src.code.model.User;

public class CheckoutService {
    private InventoryService inventory;

    public CheckoutService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void checkout(User user) throws Exception {
        for (CartItem item : user.getCartItems()) {
            Product stockProduct = inventory.getProductById(item.getProduct().getId());
            if (stockProduct != null) {
                stockProduct.reduceStock(item.getQuantity()); 
            } else {
                throw new Exception("Product not found in inventory!");
            }
        }
        System.out.println("Order placed successfully for user: " + user.getUsername());
        user.clearCart();
    }
}
