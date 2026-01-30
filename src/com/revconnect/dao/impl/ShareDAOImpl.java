package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.ShareDAO;
import com.revconnect.db.DBConnection;

public class ShareDAOImpl implements ShareDAO {

    // ---------------- SHARE POST ----------------
	@Override
	public boolean sharePost(int postId, int userId) {

	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false);

	        String sql =
	            "INSERT INTO SHARES (POST_ID, USER_ID) " +
	            "VALUES (?, ?)";

	        ps = con.prepareStatement(sql);
	        ps.setInt(1, postId);
	        ps.setInt(2, userId);

	        int rows = ps.executeUpdate();
	        con.commit();

	        System.out.println("DEBUG: Inserted rows into SHARES = " + rows);
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

    // ---------------- GET SHARE COUNT ----------------
    @Override
    public int getShareCount(int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT COUNT(*) FROM SHARES " +
                "WHERE POST_ID = ?";

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
}
