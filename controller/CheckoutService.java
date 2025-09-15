package controller;

import model.InventoryService;
import model.Product;
import view.User;

public class CheckoutService {
    private InventoryService inventory;

    public CheckoutService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void checkout(User user) throws Exception {
        for (Product p : user.getCart()) {
            Product stockProduct = inventory.getProductById(p.getId());
            if (stockProduct != null) {
                stockProduct.reduceStock(1); // assuming 1 item per add
            } else {
                throw new Exception("Product not found in inventory!");
            }
        }
        System.out.println("Order placed successfully for user: " + user.getName());
        user.clearCart();
    }
}
