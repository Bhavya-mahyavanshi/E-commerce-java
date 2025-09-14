package services;

import models.CartItem;
import models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartServices {
    private List<CartItem> cart = new ArrayList<>();

    public void addToCart(Product product, int qty){
        if(product.getStock() < qty){
            System.out.println("Not enough stock for " + product.getName());
            return;
        }

        for(CartItem item : cart){
            if(item.geProduct().getId() == product.getId()){
                item.setQuantity(item.getQuantity() + qty);
                product.reduceStock(qty);
                System.out.println("Updated " + product.getName() + " quantity.");
                return;
            }
        }
        cart.add(new CartItem(product, qty));
        product.reduceStock(qty);
        System.out.println("Added " + product.getName() + " to cart.");
    }

    public void removeFromCart(int productId){
        cart.removeIf(item -> item.geProduct().getId() == productId);
        System.out.println("Item removed from cart.");
    }

    public void viewCart(){
        if(cart.isEmpty()){
            System.out.println("Cart is empty!");
            return;
        }
        for(CartItem item : cart) {
            System.out.println(item.geProduct().getName() + " x " + item.getQuantity() + 
            " = $" + item.getTotalPrice());
        }
    }

    public double getTotal(){
        return cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
