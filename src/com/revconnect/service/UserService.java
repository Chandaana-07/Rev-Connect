package com.revconnect.service;

import com.revconnect.dao.UserDAO;

import com.revconnect.dao.impl.UserDAOImpl;
import com.revconnect.model.User;
import java.util.List;


public class UserService {
	private UserDAO dao = new UserDAOImpl();


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

    // ---------------- USER SEARCH ----------------
    public User getUserByUsername(String username) {
        return dao.getUserByUsername(username);
    }

    public User getUserByUsernameIgnoreCase(String username) {
        return dao.getUserByUsernameIgnoreCase(username);
    }

    // ---------------- DUPLICATE CHECKS ----------------
    public boolean emailExists(String email) {
        return dao.getUserByEmail(email) != null;
    }

    public boolean usernameExists(String username) {
        return dao.getUserByUsernameExact(username) != null;
    }

    // ---------------- FORGOT PASSWORD ----------------
    public boolean resetPassword(String email, String newPassword) {
        return dao.resetPassword(email, newPassword);
    }
    public List<User> searchUsers(String keyword) {
        return dao.searchUsers(keyword);
    }

    public int getUserIdByUsername(String username) {
        return dao.getUserIdByUsername(username);
    }

}
