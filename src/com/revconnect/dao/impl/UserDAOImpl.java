package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;
import com.revconnect.util.PasswordUtil;

public class UserDAOImpl implements UserDAO {

    // ---------------- LOGIN ----------------
    @Override
    public User login(Connection con, String email, String password) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
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
            close(rs, ps);
        }

        return null;
    }

    // ---------------- REGISTER ----------------
    @Override
    public boolean register(Connection con, User user) {

        PreparedStatement ps = null;

        try {
            String sql =
                "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD_HASH, ROLE) " +
                "VALUES (?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps);
        }

        return false;
    }

    // ---------------- UPDATE PROFILE ----------------
    @Override
    public boolean updateProfile(Connection con, User user) {

        PreparedStatement ps = null;

        try {
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
            close(null, ps);
        }

        return false;
    }

    // ---------------- USER SEARCH (EXACT) ----------------
    @Override
    public User getUserByUsername(Connection con, String username) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
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
            close(rs, ps);
        }

        return null;
    }

    // ---------------- USER SEARCH (CASE INSENSITIVE) ----------------
    @Override
    public User getUserByUsernameIgnoreCase(Connection con, String username) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT USER_ID, USERNAME, EMAIL, BIO, LOCATION, WEBSITE " +
                "FROM USERS WHERE UPPER(USERNAME) = UPPER(?)";

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
            close(rs, ps);
        }

        return null;
    }

    // ---------------- DUPLICATE EMAIL ----------------
    @Override
    public User getUserByEmail(Connection con, String email) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
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
            close(rs, ps);
        }

        return null;
    }

    // ---------------- DUPLICATE USERNAME ----------------
    @Override
    public User getUserByUsernameExact(Connection con, String username) {
        return getUserByUsername(con, username);
    }

    // ---------------- FORGOT PASSWORD ----------------
    @Override
    public boolean resetPassword(Connection con, String email, String newPassword) {

        PreparedStatement ps = null;

        try {
            String sql =
                "UPDATE USERS SET PASSWORD_HASH = ? WHERE EMAIL = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, PasswordUtil.hashPassword(newPassword));
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps);
        }

        return false;
    }

    // ---------------- SEARCH USERS ----------------
    @Override
    public List<User> searchUsers(Connection con, String keyword) {

        List<User> list = new ArrayList<User>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT USER_ID, USERNAME, EMAIL, BIO, LOCATION, WEBSITE " +
                "FROM USERS WHERE LOWER(USERNAME) LIKE LOWER(?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setBio(rs.getString("BIO"));
                user.setLocation(rs.getString("LOCATION"));
                user.setWebsite(rs.getString("WEBSITE"));
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps);
        }

        return list;
    }

    // ---------------- GET USER ID ----------------
    @Override
    public int getUserIdByUsername(Connection con, String username) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT USER_ID FROM USERS WHERE USERNAME = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("USER_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps);
        }

        return -1;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
