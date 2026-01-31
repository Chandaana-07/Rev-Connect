package com.revconnect.service;

import com.revconnect.dao.UserDAO;
import com.revconnect.dao.impl.UserDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.model.User;

import java.sql.Connection;
import java.util.List;

public class UserService {

    private UserDAO dao;

    public UserService() {
        dao = new UserDAOImpl();
    }

    // ---------------- REGISTER ----------------
    public boolean registerUser(User user) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.register(con, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- LOGIN ----------------
    public User loginUser(String email, String password) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.login(con, email, password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    // ---------------- UPDATE PROFILE ----------------
    public boolean updateProfile(User user) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.updateProfile(con, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- USER SEARCH ----------------
    public User getUserByUsername(String username) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUserByUsername(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    public User getUserByUsernameIgnoreCase(String username) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUserByUsernameIgnoreCase(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    // ---------------- DUPLICATE CHECKS ----------------
    public boolean emailExists(String email) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUserByEmail(con, email) != null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    public boolean usernameExists(String username) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUserByUsernameExact(con, username) != null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- FORGOT PASSWORD ----------------
    public boolean resetPassword(String email, String newPassword) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.resetPassword(con, email, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- SEARCH USERS ----------------
    public List<User> searchUsers(String keyword) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.searchUsers(con, keyword);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    // ---------------- GET USER ID ----------------
    public int getUserIdByUsername(String username) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUserIdByUsername(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return -1;
    }

    // ---------------- UTILITY ----------------
    private void close(Connection con) {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
