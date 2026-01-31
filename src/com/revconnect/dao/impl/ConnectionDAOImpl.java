package com.revconnect.dao.impl;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.ConnectionDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.UserConnection;

public class ConnectionDAOImpl implements ConnectionDAO {

    // ---------------- SEND REQUEST ----------------
   
	@Override
	public boolean sendRequest(int senderId, int receiverId, 
	                           String senderName, String receiverName) {

	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = DBConnection.getConnection();

	        String sql =
	            "INSERT INTO CONNECTIONS " +
	            "(SENDER_ID, RECEIVER_ID, SENDER_NAME, RECEIVER_NAME, STATUS, CREATED_AT) " +
	            "VALUES (?, ?, ?, ?, 'PENDING', SYSTIMESTAMP)";

	        ps = con.prepareStatement(sql);

	        ps.setInt(1, senderId);          // logged-in user's ID
	        ps.setInt(2, receiverId);       // target user's ID
	        ps.setString(3, senderName);    // logged-in user's username
	        ps.setString(4, receiverName);  // target user's username

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


    // ---------------- ACCEPT REQUEST ----------------
    @Override
    public boolean acceptRequest(int connectionId, int receiverId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE CONNECTIONS " +
                "SET STATUS = 'ACCEPTED' " +
                "WHERE CONNECTION_ID = ? " +
                "AND RECEIVER_ID = ? " +
                "AND STATUS = 'PENDING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, receiverId);

            int rows = ps.executeUpdate();

            // Debug print (optional, remove after testing)
            System.out.println("Rows updated: " + rows);

            return rows > 0;

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
 
    // ---------------- REJECT REQUEST ----------------
    @Override
    public boolean rejectRequest(int connectionId, int receiverId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE CONNECTIONS " +
                "SET STATUS = 'REJECTED' " +
                "WHERE CONNECTION_ID = ? " +
                "AND RECEIVER_ID = ? " +
                "AND STATUS = 'PENDING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, receiverId);

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

    // ---------------- VIEW PENDING REQUESTS ----------------
    @Override
    public List<UserConnection> getPendingRequests(int userId) {

        List<UserConnection> list = new ArrayList <UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT CONNECTION_ID, SENDER_ID, SENDER_NAME, RECEIVER_ID, STATUS, CREATED_AT " +
                "FROM CONNECTIONS " +
                "WHERE RECEIVER_ID = ? " +
                "AND STATUS = 'PENDING' " +
                "ORDER BY CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                UserConnection c = new UserConnection();
                c.setConnectionId(rs.getInt("CONNECTION_ID"));
                c.setSenderId(rs.getInt("SENDER_ID"));
                c.setSenderName(rs.getString("SENDER_NAME")); // IMPORTANT
                c.setReceiverId(rs.getInt("RECEIVER_ID"));
                c.setStatus(rs.getString("STATUS"));
                c.setCreatedAt(rs.getTimestamp("CREATED_AT"));

                list.add(c);
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

    @Override
    public boolean removeConnection(int connectionId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM CONNECTIONS " +
                "WHERE CONNECTION_ID = ? " +
                "AND (SENDER_ID = ? OR RECEIVER_ID = ?) " +
                "AND STATUS = 'ACCEPTED'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

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


    // ---------------- VIEW CONNECTIONS LIST ----------------
    @Override
    public List<UserConnection> getConnections(int userId) {

        List<UserConnection> list = new ArrayList<UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT CONNECTION_ID, SENDER_ID, RECEIVER_ID, STATUS, CREATED_AT " +
                "FROM CONNECTIONS " +
                "WHERE (SENDER_ID = ? OR RECEIVER_ID = ?) " +
                "AND STATUS = 'ACCEPTED'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                UserConnection c = new UserConnection();
                c.setConnectionId(rs.getInt("CONNECTION_ID"));
                c.setSenderId(rs.getInt("SENDER_ID"));
                c.setReceiverId(rs.getInt("RECEIVER_ID"));
                c.setStatus(rs.getString("STATUS"));
                c.setCreatedAt(rs.getTimestamp("CREATED_AT"));

                list.add(c);
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
    @Override
    public int getSenderIdByConnection(int connectionId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT SENDER_ID FROM CONNECTIONS " +
                "WHERE CONNECTION_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);

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

        return -1;
    }
    @Override
    public boolean removeConnectionByUser(int myUserId, int otherUserId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM CONNECTIONS " +
                "WHERE STATUS = 'ACCEPTED' " +
                "AND ((SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "  OR (SENDER_ID = ? AND RECEIVER_ID = ?))";

            ps = con.prepareStatement(sql);
            ps.setInt(1, myUserId);
            ps.setInt(2, otherUserId);
            ps.setInt(3, otherUserId);
            ps.setInt(4, myUserId);

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
