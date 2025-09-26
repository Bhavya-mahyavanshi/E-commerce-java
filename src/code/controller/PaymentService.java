package src.code.controller;

import java.util.Random;
import java.util.Scanner;

public class PaymentService {
    private Scanner sc = new Scanner(System.in);

    public boolean processPayment(double amount){
        System.out.println("\n--- Payment Gateway ---");
        System.out.println("Total amont: $" + amount);
        System.out.println("Select Payment Method:");
        System.out.println("1) Credit Card");
        System.out.println("2) Debit Card");
        System.out.println("3) UPI");
        System.out.println("4) Cash on Delivery");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
            case 2:
                System.out.println("Enter Card Number (16 digits): ");
                String card = sc.nextLine();
                if(card.length() == 16) return simulateTransaction();
                else System.out.println("Invalid card number!");
                break;
            case 3:
                System.out.println("Enter UPI ID: ");
                String upi = sc.nextLine();
                if(upi.contains("@")) return simulateTransaction();
                else System.out.println("Invalid UPI ID!");
                break;
            case 4:
                System.out.println("Cash on Delivery selected. Pay at delivery!");
                return true;
            default:
                System.out.println("Invalid option!");
        }
        return false;
    }

    private boolean simulateTransaction(){
        Random rand = new Random();
        boolean success = rand.nextBoolean();
        if(success){
            System.out.println("Payment Successful!");
        }else{
            System.out.println("Payment Failed. Please try again.");
        }

        return success;
    }
}