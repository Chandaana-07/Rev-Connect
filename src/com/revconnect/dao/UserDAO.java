package com.revconnect.dao;

import com.revconnect.model.User;
import java.util.List;


public interface UserDAO {

    boolean register(User user);

    User login(String email, String password);

    boolean updateProfile(User user);

    // Profile & Search
    User getUserByUsername(String username);
    User getUserByUsernameIgnoreCase(String username);

    // Duplicate checks
    User getUserByEmail(String email);
    User getUserByUsernameExact(String username);

    // Forgot password
    boolean resetPassword(String email, String newPassword);
    
    List<User> searchUsers(String keyword);
    int getUserIdByUsername(String username);


}
