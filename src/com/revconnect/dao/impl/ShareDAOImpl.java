package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.ShareDAO;
import com.revconnect.db.DBConnection;

public class ShareDAOImpl implements ShareDAO {

    // ================= SHARE POST =================
    @Override
    public boolean sharePost(Connection ignored, int postId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO SHARES (POST_ID, USER_ID, SHARED_AT) " +
                "VALUES (?, ?, SYSTIMESTAMP)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ================= SHARE COUNT =================
    @Override
    public int getShareCount(Connection ignored, int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT COUNT(*) FROM SHARES WHERE POST_ID = ?";

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

    // ================= UTILITY =================
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
