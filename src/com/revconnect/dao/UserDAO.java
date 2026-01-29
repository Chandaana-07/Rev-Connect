package com.revconnect.dao;

import com.revconnect.model.User;

public interface UserDAO {

    boolean register(User user);

    User login(String email, String password);

    boolean updateProfile(User user);

    // Profile & Search
    User getUserByUsername(String username);

    // Duplicate checks
    User getUserByEmail(String email);
    User getUserByUsernameExact(String username);

    // Forgot password
    boolean resetPassword(String email, String newPassword);
}
