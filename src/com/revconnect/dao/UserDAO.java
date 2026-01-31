 package com.revconnect.dao;
import java.sql.Connection;
import java.util.List;

import com.revconnect.model.User;

public interface UserDAO {

    boolean register(Connection con, User user);

    User login(Connection con, String email, String password);

    boolean updateProfile(Connection con, User user);

    User getUserByUsername(Connection con, String username);

    User getUserByUsernameIgnoreCase(Connection con, String username);

    User getUserByEmail(Connection con, String email);

    User getUserByUsernameExact(Connection con, String username);

    boolean resetPassword(Connection con, String email, String newPassword);

    List<User> searchUsers(Connection con, String keyword);

    int getUserIdByUsername(Connection con, String username);
}
