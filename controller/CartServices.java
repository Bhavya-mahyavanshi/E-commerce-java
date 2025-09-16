package controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import model.Product;

public class CartServices {
    private Map<Product, CartItem> cart = new HashMap<>();

    public void addToCart(Product product, int qty){
        if(product.getStock() < qty){
            System.out.println("Not enough stock for " + product.getName());
            return;
        }

        if(cart.containsKey(product)){
            CartItem existingItem = cart.get(product);
            existingItem.setQuantity(existingItem.getQuantity() + qty);
        }else{
            cart.put(product, new CartItem(product, qty));
        }
        product.reduceStock(qty);
        System.out.println("Added " + product.getName() + " to cart.");
    }

    public void removeFromCart(int productId){
        Product toRemove = null;

        for(Product product : cart.keySet()){
            if(product.getId() == productId){
                toRemove = product;
                break;
            }
        }

        if(toRemove != null){
            CartItem item = cart.remove(toRemove);

            if(item != null){
                toRemove.increaseStock(item.getQuantity());
                System.out.println("Removed  " + toRemove.getName() + " from cart.");
            }
        }else{
            System.out.println("Product not found in cart");
        }
    }

    public void viewCart(){
        if(cart.isEmpty()){
            System.out.println("Cart is empty!");
            return;
        }
        for(CartItem item  : cart.values()){
            System.out.println(item.geProduct().getName() + " x " + item.getQuantity() + " = $" + item.getTotalPrice());
        }
    }

    public double getTotal(){
        return cart.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public Map<Product, CartItem> getCart(){
        return cart;
    }
}
