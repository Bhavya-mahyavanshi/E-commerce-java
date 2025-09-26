package src.code.main;

import src.code.controller.AuthService;
import src.code.controller.ProductService;
import src.code.controller.CartServices;
import src.code.model.Role;
import src.code.model.User;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        // Instantiate services (no DataStore needed)
        AuthService auth = new AuthService();
        ProductService ps = new ProductService();
        CartServices cart = new CartServices();
        Scanner sc = new Scanner(System.in);

        // Test DB connection & seed default employee
        auth.seedEmployeeIfNone("admin", "admin123");
        System.out.println("Database connection test passed. Default employee seeded if none.");

        while (true) {
            System.out.println("\n1) Login\n2) Register\n3) Exit");
            String choice = sc.nextLine().trim();
            if (choice.equals("3")) break;

            if (choice.equals("2")) {
                System.out.print("username: ");
                String u = sc.nextLine();
                System.out.print("password: ");
                String p = sc.nextLine();
                System.out.print("role (customer/employee): ");
                String r = sc.nextLine();
                Role role = r.equalsIgnoreCase("employee") ? Role.EMPLOYEE : Role.CUSTOMER;
                User created = auth.register(u, p, role);
                System.out.println(created == null ? "Username already exists" : "Registered successfully");
                continue;
            }

            // Login
            System.out.print("username: ");
            String username = sc.nextLine();
            System.out.print("password: ");
            String passwd = sc.nextLine();
            User user = auth.login(username, passwd);
            if (user == null) {
                System.out.println("Login failed");
                continue;
            }

            System.out.println("Login successful! Welcome, " + user.getUsername());

            // Test menus based on role
            if (user.getRole() == Role.CUSTOMER) {
                testCustomerMenu(sc, ps, cart, user);
            } else {
                testEmployeeMenu(sc, ps);
            }
        }

        sc.close();
        System.out.println("Application exited.");
    }

    private static void testCustomerMenu(Scanner sc, ProductService ps, CartServices cart, User user) {
        while (true) {
            System.out.println("\nCustomer menu:");
            System.out.println("1) List products");
            System.out.println("2) Add to cart");
            System.out.println("3) View cart");
            System.out.println("4) Checkout");
            System.out.println("5) Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine();

            switch (c) {
                case "1" -> ps.listProducts();
                case "2" -> {
                    System.out.print("Enter product id: ");
                    int pid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter qty: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    cart.addToCart(user.getId(), pid, qty); // updated to take user id
                }
                case "3" -> cart.viewCart(user.getId());
                case "4" -> cart.checkout(user.getId());
                case "5" -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void testEmployeeMenu(Scanner sc, ProductService ps) {
        while (true) {
            System.out.println("\nEmployee menu:");
            System.out.println("1) List products");
            System.out.println("2) Add product");
            System.out.println("3) Update stock");
            System.out.println("4) Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine();

            switch (c) {
                case "1" -> ps.listProducts();
                case "2" -> {
                    System.out.print("Product name: ");
                    String name = sc.nextLine();
                    System.out.print("Price: ");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(sc.nextLine());
                    ps.addProduct(name, price, stock);
                }
                case "3" -> {
                    System.out.print("Enter product id: ");
                    int pid = Integer.parseInt(sc.nextLine());
                    System.out.print("New stock: ");
                    int stock = Integer.parseInt(sc.nextLine());
                    ps.updateStock(pid, stock);
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
