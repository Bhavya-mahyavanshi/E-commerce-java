package src.tests;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import src.code.controller.CartServices;
import src.code.model.Product;


public class CartServicesTest {

    @Test
    public void testAddToCart(){
        Product product = new Product(1, "Laptop", 1000, 5);
        CartServices cart = new CartServices();
        cart.addToCart(product, 1);

        assertEquals(1, cart.getCart().size());
    }
}
