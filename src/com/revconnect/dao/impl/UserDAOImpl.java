package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.UserDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.User;
import com.revconnect.util.PasswordUtil;

public class UserDAOImpl implements UserDAO {

    // ---------------- LOGIN ----------------
    @Override
    public User login(String email, String password) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID, USERNAME, EMAIL, ROLE " +
                "FROM USERS WHERE EMAIL = ? AND PASSWORD_HASH = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, PasswordUtil.hashPassword(password));

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setRole(rs.getString("ROLE"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // ---------------- REGISTER ----------------
    @Override
    public boolean register(User user) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD_HASH, ROLE) " +
                "VALUES (?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());

            // Store HASHED password, not plain text
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));

            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ---------------- UPDATE PROFILE ----------------
    @Override
    public boolean updateProfile(User user) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE USERS SET BIO=?, LOCATION=?, WEBSITE=? WHERE USER_ID=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, user.getBio());
            ps.setString(2, user.getLocation());
            ps.setString(3, user.getWebsite());
            ps.setInt(4, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ---------------- GET USER BY USERNAME (PROFILE VIEW) ----------------
    @Override
    public User getUserByUsername(String username) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID, USERNAME, EMAIL, BIO, LOCATION, WEBSITE " +
                "FROM USERS WHERE USERNAME = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setBio(rs.getString("BIO"));
                user.setLocation(rs.getString("LOCATION"));
                user.setWebsite(rs.getString("WEBSITE"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // ---------------- CHECK DUPLICATE EMAIL ----------------
    @Override
    public User getUserByEmail(String email) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID, USERNAME, EMAIL FROM USERS WHERE EMAIL = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmail(rs.getString("EMAIL"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // ---------------- CHECK DUPLICATE USERNAME ----------------
    @Override
    public User getUserByUsernameExact(String username) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID, USERNAME FROM USERS WHERE USERNAME = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // ---------------- FORGOT PASSWORD ----------------
    @Override
    public boolean resetPassword(String email, String newPassword) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE USERS SET PASSWORD_HASH = ? WHERE EMAIL = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, PasswordUtil.hashPassword(newPassword));
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
