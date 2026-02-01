package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.UserDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.User;

public class UserDAOImpl implements UserDAO {

    // ================= REGISTER =================
    @Override
    public boolean register(Connection ignored, User user) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "INSERT INTO users (username, email, password_hash, bio, role) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashPassword(user.getPassword())); 
            ps.setString(4, user.getBio());
            ps.setString(5, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    // ================= LOGIN =================
    @Override
    public User login(Connection ignored, String email, String password) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, hashPassword(password));

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return null;
    }

    // ================= UPDATE PROFILE =================
    @Override
    public boolean updateProfile(Connection ignored, User user) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "UPDATE users SET username = ?, bio = ?, location = ?, website = ? WHERE user_id = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getBio());
            ps.setString(3, user.getLocation());
            ps.setString(4, user.getWebsite());
            ps.setInt(5, user.getUserId()); 

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    // ================= SEARCH BY USERNAME =================
    @Override
    public User getUserByUsername(Connection ignored, String username) {
        return getUserByUsernameExact(ignored, username);
    }

    @Override
    public User getUserByUsernameIgnoreCase(Connection ignored, String username) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE LOWER(username) = LOWER(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return null;
    }

    @Override
    public User getUserByUsernameExact(Connection ignored, String username) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return null;
    }

    // ================= SEARCH BY EMAIL =================
    @Override
    public User getUserByEmail(Connection ignored, String email) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return null;
    }

    // ================= RESET PASSWORD =================
    @Override
    public boolean resetPassword(Connection ignored, String email, String newPassword) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, hashPassword(newPassword));
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    // ================= SEARCH USERS =================
    @Override
    public List<User> searchUsers(Connection ignored, String keyword) {

        List<User> list = new ArrayList<User>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE LOWER(username) LIKE LOWER(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }

    // ================= GET USER ID =================
    @Override
    public int getUserIdByUsername(Connection ignored, String username) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT user_id FROM users WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("USER_ID"); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return -1;
    }

    // ================= MAP USER =================
    private User mapUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getInt("USER_ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setRole(rs.getString("ROLE"));
        user.setBio(rs.getString("BIO"));
        user.setLocation(rs.getString("LOCATION"));
        user.setWebsite(rs.getString("WEBSITE"));

        return user;
    }

    // ================= UTIL =================
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }

    // ================= PASSWORD HASH =================
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getUsernameById(Connection con, int userId) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(
                "SELECT USERNAME FROM USERS WHERE USER_ID = ?"
            );
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("USERNAME");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return "user_" + userId;
    }

}
