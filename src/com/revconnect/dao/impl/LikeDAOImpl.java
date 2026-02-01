package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.LikeDAO;
import com.revconnect.db.DBConnection;

public class LikeDAOImpl implements LikeDAO {

    // ---------------- LIKE POST ----------------
    @Override
    public boolean likePost(int userId, int postId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO LIKES (USER_ID, POST_ID) VALUES (?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, postId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ---------------- UNLIKE POST ----------------
    @Override
    public boolean unlikePost(int userId, int postId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM LIKES WHERE USER_ID = ? AND POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, postId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ---------------- LIKE COUNT ----------------
    @Override
    public int getLikeCount(int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT COUNT(*) FROM LIKES WHERE POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return 0;
    }

    // ---------------- CHECK IF USER LIKED ----------------
    @Override
    public boolean hasUserLiked(int userId, int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT 1 FROM LIKES WHERE USER_ID = ? AND POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, postId);

            rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return false;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
