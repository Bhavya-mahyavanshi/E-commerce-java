package tests;

import org.junit.jupiter.api.Test;

import controller.CartService;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    @Test
    void testAddItem() {
        CartService cart = new CartService();
        cart.addItem("Book", 2);
        assertEquals(2, cart.getItems().get("Book"));
    }

    @Test
    void testRemoveItem() {
        CartService cart = new CartService();
        cart.addItem("Pen", 5);
        cart.removeItem("Pen");
        assertFalse(cart.getItems().containsKey("Pen"));
    }

    @Test
    void testClearCart() {
        CartService cart = new CartService();
        cart.addItem("Notebook", 3);
        cart.clearCart();
        assertTrue(cart.getItems().isEmpty());
    }
}

