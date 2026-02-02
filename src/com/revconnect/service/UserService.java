package com.revconnect.service;

import com.revconnect.dao.UserDAO;
import com.revconnect.dao.impl.UserDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.model.User;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;


import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;

public class UserService {
	private static final Logger logger = Logger.getLogger(UserService.class);
    private UserDAO dao;
    private ConnectionProvider connectionProvider;


    public UserService() {
        dao = new UserDAOImpl();
        connectionProvider = new com.revconnect.db.DefaultConnectionProvider();
    }

    // For Mockito / Testing
    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    // ---------------- REGISTER ----------------
    public boolean registerUser(User user) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return false;
            }

            return dao.register(con, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- LOGIN ----------------
    public User loginUser(String email, String password) {

        Connection con = null;
        try {
        	 logger.info("Login attempt started for user: " + email);
            con = connectionProvider.getConnection();

            if (con == null) {
                logger.error("Database connection failed during login");
                System.out.println("Database is currently unavailable.");
                return null;
            }
            User user = dao.login(con, email, password);

            if (user != null) {
                logger.info("Login successful for user: " + user.getUsername());
            } else {
                logger.warn("Login failed - invalid credentials for: " + email);
            }

            return user;


        } catch (Exception e) {
        	  logger.error("Exception during login for user: " + email, e);
            e.printStackTrace();
        }
        return null;
    }
    

    // ---------------- UPDATE PROFILE ----------------
    public boolean updateProfile(User user) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return false;
            }

            return dao.updateProfile(con, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- USER SEARCH ----------------
    public User getUserByUsername(String username) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return null;
            }

            return dao.getUserByUsername(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsernameIgnoreCase(String username) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return null;
            }

            return dao.getUserByUsernameIgnoreCase(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- DUPLICATE CHECKS ----------------
    public boolean emailExists(String email) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return false;
            }

            return dao.getUserByEmail(con, email) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean usernameExists(String username) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return false;
            }

            return dao.getUserByUsernameExact(con, username) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- FORGOT PASSWORD ----------------
    public boolean resetPassword(String email, String newPassword) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return false;
            }

            return dao.resetPassword(con, email, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- SEARCH USERS ----------------
    public List<User> searchUsers(String keyword) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return null;
            }

            return dao.searchUsers(con, keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- GET USER ID ----------------
    public int getUserIdByUsername(String username) {

        Connection con = null;
        try {
            con = connectionProvider.getConnection();

            if (con == null) {
                System.out.println("Database is currently unavailable.");
                return -1;
            }

            return dao.getUserIdByUsername(con, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
 // --- Added for Notifications / Likes ---
    public String getUsernameById(int userId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getUsernameById(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return "user_" + userId; // fallback
    }
 // For Mockito testing
    public UserService(UserDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
    }


}
