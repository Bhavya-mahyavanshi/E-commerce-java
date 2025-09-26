package src.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import src.code.controller.CartServices;
import src.code.controller.ProductService;
import src.code.model.Product;
import src.code.Database.databaseconnection;

public class CartServicesTest {

    private static int testUserId = 9999;     // Dummy test user
    private static int testProductId = 8888;  // Dummy test product

    private CartServices cartServices;
    private ProductService productService;

    @BeforeEach
    void setUp() throws SQLException {
        cartServices = new CartServices();
        productService = new ProductService();

        try (Connection conn = databaseconnection.getConnection()) {
            // Clean test data before each run
            conn.prepareStatement("DELETE FROM carts WHERE user_id=" + testUserId).executeUpdate();
            conn.prepareStatement("DELETE FROM products WHERE id=" + testProductId).executeUpdate();

            // Insert a dummy product
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO products (id, name, price, stock) VALUES (?, ?, ?, ?)");
            ps.setInt(1, testProductId);
            ps.setString(2, "Test Product");
            ps.setDouble(3, 10.0);
            ps.setInt(4, 100);
            ps.executeUpdate();
        }
    }

    @Test
    void testAddToCartInsertsNewProduct() throws SQLException {
        cartServices.addToCart(testUserId, testProductId, 2);

        try (Connection conn = databaseconnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT quantity FROM carts WHERE user_id=? AND product_id=?");
            ps.setInt(1, testUserId);
            ps.setInt(2, testProductId);
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next(), "Cart entry should exist");
            assertEquals(2, rs.getInt("quantity"), "Quantity should match added value");
        }

        Product product = productService.findById(testProductId);
        assertEquals(98, product.getStock(), "Stock should be reduced after adding to cart");
    }

    @Test
    void testAddToCartUpdatesExistingProduct() throws SQLException {
        // First add
        cartServices.addToCart(testUserId, testProductId, 2);
        // Add again
        cartServices.addToCart(testUserId, testProductId, 3);

        try (Connection conn = databaseconnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT quantity FROM carts WHERE user_id=? AND product_id=?");
            ps.setInt(1, testUserId);
            ps.setInt(2, testProductId);
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next(), "Cart entry should exist");
            assertEquals(5, rs.getInt("quantity"), "Quantity should be updated to 5");
        }

        Product product = productService.findById(testProductId);
        assertEquals(95, product.getStock(), "Stock should be reduced correctly after multiple adds");
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = databaseconnection.getConnection()) {
            conn.prepareStatement("DELETE FROM carts WHERE user_id=" + testUserId).executeUpdate();
            conn.prepareStatement("DELETE FROM products WHERE id=" + testProductId).executeUpdate();
        }
    }
}
