package com.revconnect.service;

import com.revconnect.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private final String FILE = "users.dat";

    // Save all users
    private void saveAll(List<User> users) {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(users);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving users");
        }
    }

    // Load users
    public List<User> getAllUsers() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE));
            return (List<User>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<User>();
        }
    }

    // Register
    public void register(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveAll(users);
        System.out.println("Registration Successful!");
    }

    // Login with email OR username
    public User login(String input, String password) {
        for (User u : getAllUsers()) {
            if ((u.getEmail().equalsIgnoreCase(input) ||
                 u.getUsername().equalsIgnoreCase(input)) &&
                 u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    // Change password
    public boolean changePassword(User user,
                                  String oldPass,
                                  String newPass) {

        if (!user.checkPassword(oldPass)) {
            return false;
        }

        user.setNewPassword(newPass);

        List<User> users = getAllUsers();
        saveAll(users);
        return true;
    }

    // Recover password using security answer
    public boolean recoverPassword(String input,
                                   String answer,
                                   String newPass) {

        List<User> users = getAllUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(input) ||
                u.getUsername().equalsIgnoreCase(input)) {

                if (u.checkSecurityAnswer(answer)) {
                    u.setNewPassword(newPass);
                    saveAll(users);
                    return true;
                }
            }
        }
        return false;
    }
}
