package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.FollowDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Follow;

public class FollowDAOImpl implements FollowDAO {

    // ---------------- FOLLOW USER ----------------
    @Override
    public boolean followUser(int followerId, int followingId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sql =
                "INSERT INTO FOLLOW (FOLLOWER_ID, FOLLOWING_ID) " +
                "VALUES (?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            int rows = ps.executeUpdate();
            con.commit();

            System.out.println("DEBUG: Inserted rows into FOLLOW = " + rows);
            return rows == 1;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

    // ---------------- UNFOLLOW USER ----------------
    @Override
    public boolean unfollowUser(int followerId, int followingId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sql =
                "DELETE FROM FOLLOW " +
                "WHERE FOLLOWER_ID = ? AND FOLLOWING_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            int rows = ps.executeUpdate();
            con.commit();
            return rows > 0;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

    // ---------------- GET MY FOLLOWERS ----------------
    @Override
    public List<Follow> getFollowers(int userId) {

        List<Follow> list = new ArrayList<Follow>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT f.FOLLOWER_ID, u.USERNAME, f.CREATED_AT " +
                "FROM FOLLOW f " +
                "JOIN USERS u ON f.FOLLOWER_ID = u.USER_ID " +
                "WHERE f.FOLLOWING_ID = ? " +
                "ORDER BY f.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Follow f = new Follow();
                f.setFollowerId(rs.getInt("FOLLOWER_ID"));
                f.setUsername(rs.getString("USERNAME"));
                f.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(f);
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

        return list;
    }

    // ---------------- GET WHO I FOLLOW ----------------
    @Override
    public List<Follow> getFollowing(int userId) {

        List<Follow> list = new ArrayList<Follow>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT f.FOLLOWING_ID, u.USERNAME, f.CREATED_AT " +
                "FROM FOLLOW f " +
                "JOIN USERS u ON f.FOLLOWING_ID = u.USER_ID " +
                "WHERE f.FOLLOWER_ID = ? " +
                "ORDER BY f.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Follow f = new Follow();
                f.setFollowingId(rs.getInt("FOLLOWING_ID"));
                f.setUsername(rs.getString("USERNAME"));
                f.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(f);
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

        return list;
    }

    // ---------------- CHECK IF ALREADY FOLLOWING ----------------
    @Override
    public boolean isFollowing(int followerId, int followingId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT 1 FROM FOLLOW " +
                "WHERE FOLLOWER_ID = ? AND FOLLOWING_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);

            rs = ps.executeQuery();
            return rs.next();

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

        return false;
    }
}
