package com.revconnect.ui;

import com.revconnect.model.User;
import com.revconnect.service.AuthService;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class AuthMenu {

    private Scanner sc = new Scanner(System.in);
    private AuthService service = new AuthService();
    private User loggedUser;
    Logger logger =
            Logger.getLogger(AuthMenu.class);

    public void start() {
        int choice;
        
        logger.info("Auth menu started");

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
                	logger.info("Selected Register option");
                    register();
                    break;
                case 2:
                	logger.info("Selected Login option");
                    login();
                    break;
                case 3:
                	logger.info("Selected Forgot Password option");
                    recover();
                    break;
                case 4:
                	 logger.info("User exited application");
                    System.out.println("Goodbye!");
                    break;
                default:
                	logger.warn("Invalid menu choice: " + choice);
                    System.out.println("Invalid choice");
            }
        } while (choice != 4);
    }

    // -------- Register --------
    private void register() {
    	try {
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

            logger.info("Registering user: " + user.getUsername());

            service.register(user);

            logger.info("Registration completed for user: " + user.getUsername());
        } catch (Exception e) {
            logger.error("Error during registration", e);
        }
    }

    // -------- Login --------
    private void login() {
    	try {
            System.out.print("Enter Email or Username: ");
            String input = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            logger.info("Login attempt for: " + input);

            loggedUser = service.login(input, pass);

            if (loggedUser != null) {
                logger.info("Login successful for user: " + loggedUser.getUsername());
                System.out.println("Login Successful! Welcome "
                        + loggedUser.getUsername());
                settingsMenu();
            } else {
                logger.warn("Login failed for: " + input);
                System.out.println("Invalid Credentials");
            }
        } catch (Exception e) {
            logger.error("Login error", e);
        }
    }

    // -------- Forgot Password --------
    private void recover() {
    	try {
            System.out.print("Enter Email or Username: ");
            String input = sc.nextLine();

            System.out.print("Enter Security Answer: ");
            String ans = sc.nextLine();

            System.out.print("Enter New Password: ");
            String newPass = sc.nextLine();

            logger.info("Password recovery attempt for: " + input);

            if (service.recoverPassword(input, ans, newPass)) {
                logger.info("Password recovery successful for: " + input);
                System.out.println("Password Reset Successful!");
            } else {
                logger.warn("Password recovery failed for: " + input);
                System.out.println("Recovery Failed!");
            }
        } catch (Exception e) {
            logger.error("Recovery error", e);
        }
    }

    // -------- Account Settings --------
    private void settingsMenu() {
    	int choice;

        logger.info("Entered settings menu for user: " +
                (loggedUser != null ? loggedUser.getUsername() : "unknown"));

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
                    logger.info("Selected Change Password");
                    changePassword();
                    break;
                case 2:
                    loggedUser.togglePrivacy();
                    logger.info("Privacy toggled for user: " +
                            loggedUser.getUsername() +
                            " -> " +
                            (loggedUser.isPrivate() ? "PRIVATE" : "PUBLIC"));

                    System.out.println("Profile is now: "
                            + (loggedUser.isPrivate()
                            ? "PRIVATE" : "PUBLIC"));
                    break;
                case 3:
                    logger.info("User logged out: " +
                            (loggedUser != null
                            ? loggedUser.getUsername()
                            : "unknown"));
                    loggedUser = null;
                    System.out.println("Logged out.");
                    break;
                default:
                    logger.warn("Invalid settings choice: " + choice);
                    System.out.println("Invalid choice");
            }
        } while (choice != 3);
    }

    private void changePassword() {
    	try {
            System.out.print("Enter Current Password: ");
            String oldPass = sc.nextLine();

            System.out.print("Enter New Password: ");
            String newPass = sc.nextLine();

            logger.info("Password change attempt for user: " +
                    (loggedUser != null
                    ? loggedUser.getUsername()
                    : "unknown"));

            if (service.changePassword(loggedUser, oldPass, newPass)) {
                logger.info("Password changed successfully for user: " +
                        loggedUser.getUsername());
                System.out.println("Password Changed Successfully!");
            } else {
                logger.warn("Password change failed for user: " +
                        loggedUser.getUsername());
                System.out.println("Current Password Incorrect!");
            }
        } catch (Exception e) {
            logger.error("Change password error", e);
        }
    }
}
