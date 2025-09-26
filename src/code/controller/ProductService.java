package src.code.controller;

import src.code.model.Product;
import src.code.Database.databaseconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public void addProduct(String name, double price, int stock) {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        try (Connection conn = databaseconnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("✅ Product added: " + name + " (ID: " + id + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStock(int productId, int newStock) {
        String sql = "UPDATE products SET quantity=? WHERE id=?";
        try (Connection conn = databaseconnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);
            int updated = stmt.executeUpdate();
            if (updated > 0) System.out.println("✅ Stock updated for product ID " + productId);
            else System.out.println("⚠️ Product not found.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection conn = databaseconnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void listProducts() {
        List<Product> products = getAll();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products available.");
            return;
        }
        System.out.println("\n📦 Available Products:");
        for (Product p : products) {
            System.out.println(
                    p.getId() + " | " + p.getName() + " | $" + p.getPrice() + " | stock: " + p.getStock()
            );
        }
    }
}
