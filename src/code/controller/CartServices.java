package src.code.controller;

import src.code.model.Product;
import src.code.Database.databaseconnection;

import java.sql.*;

public class CartServices {

    public void addToCart(int userId, int productId, int qty) {
        // Check stock
        ProductService ps = new ProductService();
        Product product = ps.findById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (product.getStock() < qty) {
            System.out.println("Not enough stock for " + product.getName());
            return;
        }

        // Check if product already in cart
        String selectSql = "SELECT quantity FROM carts WHERE user_id=? AND product_id=?";
        String insertSql = "INSERT INTO carts (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE carts SET quantity=? WHERE user_id=? AND product_id=?";

        try (Connection conn = databaseconnection.getConnection()) {
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, productId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int existingQty = rs.getInt("quantity");
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, existingQty + qty);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, productId);
                updateStmt.executeUpdate();
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, productId);
                insertStmt.setInt(3, qty);
                insertStmt.executeUpdate();
            }

            // Reduce stock in products table
            ps.updateStock(productId, product.getStock() - qty);

            System.out.println("Added " + product.getName() + " x" + qty + " to cart.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCart(int userId) {
        String sql = """
            SELECT p.name, p.price, c.quantity
            FROM carts c
            JOIN products p ON c.product_id = p.id
            WHERE c.user_id = ?
            """;

        double total = 0;
        try (Connection conn = databaseconnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nYour Cart:");
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int qty = rs.getInt("quantity");
                double itemTotal = price * qty;
                total += itemTotal;
                System.out.println(name + " x " + qty + " = $" + itemTotal);
            }
            System.out.println("Total: $" + total);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkout(int userId) {
        String deleteSql = "DELETE FROM carts WHERE user_id=?";
        double total = 0;

        String totalSql = """
            SELECT SUM(p.price * c.quantity) as total
            FROM carts c
            JOIN products p ON c.product_id = p.id
            WHERE c.user_id = ?
            """;

        try (Connection conn = databaseconnection.getConnection()) {

            PreparedStatement totalStmt = conn.prepareStatement(totalSql);
            totalStmt.setInt(1, userId);
            ResultSet rs = totalStmt.executeQuery();
            if (rs.next()) total = rs.getDouble("total");

            if(total <= 0){
                System.out.println("\nYour cart is empty!");
                return;
            }

            PaymentService paymentService = new PaymentService();
            boolean paid = paymentService.processPayment(total);

            if(paid){
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();

                System.out.println("\nCheckout successful! You paid: $" + total);
            }else{
                System.out.println("\nCheckout failed. Payment was not completed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
