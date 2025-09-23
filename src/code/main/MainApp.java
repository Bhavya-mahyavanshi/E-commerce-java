package src.code.main;

import src.code.controller.*;
import src.code.model.Role;
import src.code.model.User;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args){
        DataStore ds = new DataStore();
        AuthService auth = new AuthService(ds);
        ProductService ps = new ProductService(ds);
        CartServices cart = new CartServices();
        Scanner sc = new Scanner(System.in);

        // seed default employee if none
        auth.seedEmployeeIfNone("admin", "admin123");

        while(true){
            System.out.println("1) Login\n2) Register\n3) Exit");
            String choice = sc.nextLine().trim();
            if(choice.equals("3")) break;

            if(choice.equals("2")){
                System.out.print("username: "); String u = sc.nextLine();
                System.out.print("password: "); String p = sc.nextLine();
                System.out.print("role (customer/employee): "); String r = sc.nextLine();
                Role role = r.equalsIgnoreCase("employee") ? Role.EMPLOYEE : Role.CUSTOMER;
                User created = auth.register(u, p, role);
                System.out.println(created==null ? "exists" : "registered");
                continue;
            }

            // login
            System.out.print("username: "); String username = sc.nextLine();
            System.out.print("password: "); String passwd = sc.nextLine();
            User user = auth.login(username, passwd);
            if(user == null){
                System.out.println("Login failed");
                continue;
            }

            if(user.getRole() == Role.CUSTOMER){
                customerMenu(sc, ps, cart);
            } else {
                employeeMenu(sc, ps);
            }
        }

        sc.close();
    }

    // implement customerMenu and employeeMenu as small loops that call ProductService and CartServices methods
    private static void customerMenu(Scanner sc, ProductService ps, CartServices cart){
        while(true){
            System.out.println("Customer menu: 1) List products 2) Add to cart 3) View cart 4) Checkout 5) Logout");
            String c = sc.nextLine();
            if(c.equals("5")) break;
            // implement each option reading productId, qty etc and calling services
        }
    }

    private static void employeeMenu(Scanner sc, ProductService ps){
        while(true){
            System.out.println("Employee menu: 1) List products 2) Add product 3) Update stock 4) Logout");
            String c = sc.nextLine();
            if(c.equals("4")) break;
            // implement actions
        }
    }
}
