package com.revconnect.service;

import com.revconnect.dao.UserDAO;
import com.revconnect.dao.impl.UserDAOImpl;
import com.revconnect.model.User;

public class UserService {

    private UserDAO dao;

    public UserService() {
        dao = new UserDAOImpl();
    }

    // ---------------- REGISTER ----------------
    public boolean registerUser(User user) {
        return dao.register(user);
    }

    // ---------------- LOGIN ----------------
    public User loginUser(String email, String password) {
        return dao.login(email, password);
    }

    // ---------------- UPDATE PROFILE ----------------
    public boolean updateProfile(User user) {
        return dao.updateProfile(user);
    }

    // ---------------- GET USER BY USERNAME ----------------
    public User getUserByUsername(String username) {
        return dao.getUserByUsername(username);
    }

    // ---------------- DUPLICATE EMAIL CHECK ----------------
    public boolean emailExists(String email) {
        return dao.getUserByEmail(email) != null;
    }

    // ---------------- USERNAME CHECK ----------------
    public boolean usernameExists(String username) {
        return dao.getUserByUsernameExact(username) != null;
    }

    // ---------------- FORGOT PASSWORD ----------------
    public boolean resetPassword(String email, String newPassword) {
        return dao.resetPassword(email, newPassword);
    }
}
