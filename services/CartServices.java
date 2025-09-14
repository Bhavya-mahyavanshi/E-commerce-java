package services;

import models.CartItem;
import models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartServices {
    private List<CartItem> cart = new ArrayList<>();

    public void addToCart(Product product, int qty){
        cart.add(new CartItem(product, qty));
        product.reduceStock(qty);
    }

    public void removeFromCart(int productId){
        cart.removeIf(item -> item.geProduct().getId() == productId);
    }

    public void viewCart(){
        for(CartItem item : cart) {
            System.out.println(item.geProduct().getName() + " x " + item.getQuantity() + 
            " = $" + item.getTotalPrice());
        }
    }

    public double getTotal(){
        return cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
