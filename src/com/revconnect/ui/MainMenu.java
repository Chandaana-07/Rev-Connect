package com.revconnect.ui;

import java.util.Scanner;

import com.revconnect.model.User;
import com.revconnect.service.UserService;

public class MainMenu {

    private UserService userService = new UserService();
    private Scanner sc = new Scanner(System.in);

    // ===================== MAIN MENU =====================
    public void showMenu() {

        while (true) {
            System.out.println("\n===== Welcome to RevConnect =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println("Thank you for using RevConnect!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== SAFE INPUT =====================
    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    // ===================== REGISTER =====================
    private void register() {

        User user = new User();

        // -------- Username --------
        System.out.print("Username: ");
        user.setUsername(sc.nextLine().trim());

        // -------- Email Validation --------
        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Example: user@gmail.com");
            } else {
                break;
            }
        }

        // -------- Duplicate Email Check --------
        if (userService.emailExists(email)) {
            System.out.println("Email already registered. Please login instead.");
            return;
        }

        user.setEmail(email);

        // -------- Password Strength Validation --------
        String password;
        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine().trim();

            if (!isStrongPassword(password)) {
                System.out.println("Password must be at least 6 characters and contain letters and numbers.");
            } else {
                break;
            }
        }

        user.setPassword(password);

        // -------- Role --------
        System.out.print("Role (PERSONAL/CREATOR/BUSINESS): ");
        user.setRole(sc.nextLine().trim());

        boolean success = userService.registerUser(user);

        if (success) {
            System.out.println("Registration Successful!");
        } else {
            System.out.println("Registration Failed");
        }
    }

    // ===================== LOGIN =====================
    private void login() {

        // -------- Email Validation --------
        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = sc.nextLine().trim();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Example: user@gmail.com");
            } else {
                break;
            }
        }

        System.out.print("Enter Password: ");
        String password = sc.nextLine().trim();

        User user = userService.loginUser(email, password);

        if (user != null) {
            System.out.println("Login Successful!");
            System.out.println("Welcome " + user.getUsername());

           
            UserMenu userMenu = new UserMenu(user);
            userMenu.showMenu();
        } else {
            // ===================== WRONG PASSWORD FLOW =====================
            System.out.println("Wrong email or password!");

            while (true) {
                System.out.println("1. Try Again");
                System.out.println("2. Forgot Password");
                System.out.println("3. Back to Main Menu");
                System.out.print("Choose: ");

                int opt = readInt();

                if (opt == 1) {
                    login();
                    return;
                } else if (opt == 2) {
                    forgotPassword();
                    return;
                } else if (opt == 3) {
                    return;
                } else {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    // ===================== FORGOT PASSWORD =====================
    private void forgotPassword() {

        String email;
        while (true) {
            System.out.print("Enter registered email: ");
            email = sc.nextLine().trim();

            if (!isValidEmail(email)) {
                System.out.println("Invalid email format.");
            } else {
                break;
            }
        }

        String newPassword;
        while (true) {
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine().trim();

            if (!isStrongPassword(newPassword)) {
                System.out.println("Password must be at least 6 characters and contain letters and numbers.");
            } else {
                break;
            }
        }

        boolean success = userService.resetPassword(email, newPassword);

        if (success) {
            System.out.println("Password reset successful! Please login.");
        } else {
            System.out.println("Email not found.");
        }
    }

    // ===================== EMAIL VALIDATION =====================
    private boolean isValidEmail(String email) {

        if (email == null) return false;

        email = email.trim();
        if (email.length() == 0) return false;

        // Simple, safe regex for student projects
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return email.matches(emailRegex);
    }

    // ===================== PASSWORD STRENGTH =====================
    private boolean isStrongPassword(String password) {

        if (password == null || password.length() < 6) return false;

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasLetter && hasDigit;
    }
}
