package main;

import controller.CheckoutService;
import model.InventoryService;
import model.Product;
import view.User;
import view.UserService;

public class App {
    public static void main(String[] args) {
       
        InventoryService inventory = new InventoryService();
        UserService userService = new UserService();
        CheckoutService checkout = new CheckoutService(inventory);

        
        inventory.addProduct(new Product(1, "Laptop", 1200.0, 5));
        inventory.addProduct(new Product(2, "Phone", 800.0, 10));
        
        User user1 = new User(101, "Bhavya");
        userService.addUser(user1);

        
        user1.addToCart(inventory.getProductById(1));
        user1.addToCart(inventory.getProductById(2));

        
        try {
            checkout.checkout(user1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
