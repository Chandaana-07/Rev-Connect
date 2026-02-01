package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.ConnectionDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.UserConnection;

public class ConnectionDAOImpl implements ConnectionDAO {

    // ================= REQUEST SYSTEM =================

    @Override
    public boolean sendRequest(int senderId, int receiverId, String senderName, String receiverName) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "INSERT INTO connections " +
                         "(sender_id, receiver_id, status, sender_name, receiver_name) " +
                         "VALUES (?, ?, 'PENDING', ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, senderName);
            ps.setString(4, receiverName);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    @Override
    public List<UserConnection> getPendingRequests(int userId) {

        List<UserConnection> list = new ArrayList<UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM connections " +
                         "WHERE receiver_id = ? AND status = 'PENDING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapConnection(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }

    @Override
    public boolean acceptRequest(int connectionId, int receiverId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "UPDATE connections SET status = 'ACCEPTED' " +
                         "WHERE connection_id = ? AND receiver_id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, receiverId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    @Override
    public boolean rejectRequest(int connectionId, int receiverId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "DELETE FROM connections " +
                         "WHERE connection_id = ? AND receiver_id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, receiverId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    @Override
    public List<UserConnection> getConnections(int userId) {

        List<UserConnection> list = new ArrayList<UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM connections " +
                         "WHERE (sender_id = ? OR receiver_id = ?) " +
                         "AND status = 'ACCEPTED'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapConnection(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }

    @Override
    public boolean removeConnection(int connectionId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "DELETE FROM connections " +
                         "WHERE connection_id = ? " +
                         "AND (sender_id = ? OR receiver_id = ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    // ================= FOLLOW SYSTEM =================

    @Override
    public boolean followUser(int fromUser, int toUser) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            // Prevent duplicate follows
            String checkSql = "SELECT 1 FROM connections " +
                              "WHERE sender_id = ? AND receiver_id = ? AND status = 'FOLLOWING'";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, fromUser);
            checkPs.setInt(2, toUser);

            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                rs.close();
                checkPs.close();
                return false; // already following
            }
            rs.close();
            checkPs.close();

            String sql = "INSERT INTO connections " +
                         "(sender_id, receiver_id, status, sender_name, receiver_name) " +
                         "VALUES (?, ?, 'FOLLOWING', " +
                         "(SELECT username FROM users WHERE user_id = ?), " +
                         "(SELECT username FROM users WHERE user_id = ?))";

            ps = con.prepareStatement(sql);
            ps.setInt(1, fromUser);
            ps.setInt(2, toUser);
            ps.setInt(3, fromUser);
            ps.setInt(4, toUser);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }

    @Override
    public boolean unfollowUser(int fromUser, int toUser) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql = "DELETE FROM connections " +
                         "WHERE sender_id = ? AND receiver_id = ? AND status = 'FOLLOWING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, fromUser);
            ps.setInt(2, toUser);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
        return false;
    }
    

    @Override
    public List<UserConnection> getFollowers(int userId) {

        List<UserConnection> list = new ArrayList<UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM connections " +
                         "WHERE receiver_id = ? AND status = 'FOLLOWING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapConnection(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }
    @Override
    public List<UserConnection> getFollowing(int userId) {

        List<UserConnection> list = new ArrayList<UserConnection>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT * FROM connections " +
                         "WHERE sender_id = ? AND status = 'FOLLOWING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapConnection(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }

    @Override
    public int getFollowerCount(int userId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM connections " +
                         "WHERE receiver_id = ? AND status = 'FOLLOWING'";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return 0;
    }

    // ================= EXTRA HELPERS =================

    @Override
    public int getSenderIdByConnection(int connectionId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT sender_id FROM connections WHERE connection_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, connectionId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SENDER_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return -1;
    }

    @Override
    public boolean removeConnectionByUser(int connectionId, int userId) {
        return removeConnection(connectionId, userId);
    }

    // ================= MAP CONNECTION =================
    private UserConnection mapConnection(ResultSet rs) throws java.sql.SQLException {

        UserConnection c = new UserConnection();

        c.setConnectionId(rs.getInt("CONNECTION_ID"));
        c.setSenderId(rs.getInt("SENDER_ID"));
        c.setReceiverId(rs.getInt("RECEIVER_ID"));
        c.setStatus(rs.getString("STATUS"));
        c.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        c.setSenderName(rs.getString("SENDER_NAME"));
        c.setReceiverName(rs.getString("RECEIVER_NAME"));

        return c;
    }

    // ================= UTIL =================
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
