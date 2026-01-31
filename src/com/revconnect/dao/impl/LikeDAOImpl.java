package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.LikeDAO;
import com.revconnect.db.DBConnection;

public class LikeDAOImpl implements LikeDAO {

    @Override
    public boolean likePost(int postId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            if (hasUserLiked(con, postId, userId)) {
                return false;
            }

            String sql =
                "INSERT INTO LIKES (POST_ID, USER_ID) VALUES (?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

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

    @Override
    public boolean unlikePost(int postId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM LIKES WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

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
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    @Override
    public boolean hasUserLiked(Connection con, int postId, int userId) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT 1 FROM LIKES WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


}
