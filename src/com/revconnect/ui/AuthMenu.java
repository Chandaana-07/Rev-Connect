package com.revconnect.ui;

import com.revconnect.model.User;
import com.revconnect.service.AuthService;

import java.util.Scanner;

public class AuthMenu {

    private Scanner sc = new Scanner(System.in);
    private AuthService service = new AuthService();
    private User loggedUser;

    public void start() {
        int choice;

        do {
            System.out.println("\n===== AUTH MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    recover();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 4);
    }

    // -------- Register --------
    private void register() {
        User user = new User();

        System.out.print("Username: ");
        user.setUsername(sc.nextLine());

        System.out.print("Email: ");
        user.setEmail(sc.nextLine());

        System.out.print("Password: ");
        user.setPassword(sc.nextLine());

        System.out.print("Security Question: ");
        user.setSecurityQuestion(sc.nextLine());

        System.out.print("Security Answer: ");
        user.setSecurityAnswer(sc.nextLine());

        service.register(user);
    }

    // -------- Login --------
    private void login() {
        System.out.print("Enter Email or Username: ");
        String input = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        loggedUser = service.login(input, pass);

        if (loggedUser != null) {
            System.out.println("Login Successful! Welcome "
                    + loggedUser.getUsername());
            settingsMenu();
        } else {
            System.out.println("Invalid Credentials");
        }
    }

    // -------- Forgot Password --------
    private void recover() {
        System.out.print("Enter Email or Username: ");
        String input = sc.nextLine();

        System.out.print("Enter Security Answer: ");
        String ans = sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPass = sc.nextLine();

        if (service.recoverPassword(input, ans, newPass)) {
            System.out.println("Password Reset Successful!");
        } else {
            System.out.println("Recovery Failed!");
        }
    }

    // -------- Account Settings --------
    private void settingsMenu() {
        int choice;

        do {
            System.out.println("\n===== ACCOUNT SETTINGS =====");
            System.out.println("1. Change Password");
            System.out.println("2. Toggle Privacy (Public/Private)");
            System.out.println("3. Logout");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 2:
                    loggedUser.togglePrivacy();
                    System.out.println("Profile is now: "
                            + (loggedUser.isPrivate()
                            ? "PRIVATE" : "PUBLIC"));
                    break;
                case 3:
                    loggedUser = null;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    private void changePassword() {
        System.out.print("Enter Current Password: ");
        String oldPass = sc.nextLine();

        System.out.print("Enter New Password: ");
        String newPass = sc.nextLine();

        if (service.changePassword(loggedUser, oldPass, newPass)) {
            System.out.println("Password Changed Successfully!");
        } else {
            System.out.println("Current Password Incorrect!");
        }
    }
}
