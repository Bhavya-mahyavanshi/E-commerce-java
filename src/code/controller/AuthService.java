package src.code.controller;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.code.model.Role;
import src.code.model.User;
import src.code.Database.databaseconnection;

public class AuthService {

    // Check if a username already exists
    private boolean userExists(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Register a new user with role input as String (user input case-insensitive)
    public User register(String username, String plainPassword, String roleInput) {
        if (userExists(username)) return null;

        String hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        Role role;
        try {
            role = Role.valueOf(roleInput.trim().toUpperCase()); // case-insensitive
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role! Use CUSTOMER or EMPLOYEE.");
            return null;
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.setString(2, hash);
            stmt.setString(3, role.toString());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) id = rs.getInt(1);

            return new User(id, username, hash, role);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Overloaded register method to accept Role object (for existing code)
    public User register(String username, String plainPassword, Role role) {
        return register(username, plainPassword, role.toString());
    }

    // Login user
    public User login(String username, String plainPassword) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("password");
                Role role;
                try {
                    role = Role.valueOf(rs.getString("role").toUpperCase()); // case-insensitive
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid role stored in database!");
                    return null;
                }
                int id = rs.getInt("id");

                if (BCrypt.checkpw(plainPassword, hash)) {
                    return new User(id, username, hash, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Seed default employee if none exists
    public void seedEmployeeIfNone(String defaultUsername, String defaultPassword) {
        String sql = "SELECT * FROM users WHERE role='EMPLOYEE' LIMIT 1";
        try (Connection conn = databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.next()) {
                register(defaultUsername, defaultPassword, Role.EMPLOYEE); // now works
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
