package src.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import src.code.controller.CartServices;
import src.code.controller.ProductService;
import src.code.model.Product;
import src.code.Database.databaseconnection;

public class CartServicesTest {

    private static int testUserId = 9999;
    private static int testProductId = 8888;
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

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = databaseconnection.getConnection()) {
            conn.prepareStatement("DELETE FROM carts WHERE user_id=" + testUserId).executeUpdate();
            conn.prepareStatement("DELETE FROM products WHERE id=" + testProductId).executeUpdate();
        }
    }
}
