package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Notification;

public class NotificationDAOImpl implements NotificationDAO {

    // ---------------- CREATE NOTIFICATION ----------------
    @Override
    public boolean createNotification(Notification n) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO NOTIFICATIONS (USER_ID, MESSAGE, IS_READ) " +
                "VALUES (?, ?, 0)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, n.getUserId());
            ps.setString(2, n.getMessage());

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

    // ---------------- GET MY NOTIFICATIONS ----------------
    @Override
    public List<Notification> getMyNotifications(int userId) {

        List<Notification> list = new ArrayList<Notification>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT NOTIF_ID, USER_ID, MESSAGE, IS_READ, CREATED_AT " +
                "FROM NOTIFICATIONS " +
                "WHERE USER_ID = ? " +
                "ORDER BY CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotifId(rs.getInt("NOTIF_ID"));
                n.setUserId(rs.getInt("USER_ID"));
                n.setMessage(rs.getString("MESSAGE"));
                n.setRead(rs.getInt("IS_READ") == 1);
                n.setCreatedAt(rs.getTimestamp("CREATED_AT"));

                list.add(n);
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

    // ---------------- MARK AS READ ----------------
    @Override
    public boolean markAsRead(int notifId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE NOTIFICATIONS " +
                "SET IS_READ = 1 " +
                "WHERE NOTIF_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, notifId);
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

    // ---------------- UNREAD COUNT ----------------
    @Override
    public int getUnreadCount(int userId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT COUNT(*) FROM NOTIFICATIONS " +
                "WHERE USER_ID = ? AND IS_READ = 0";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

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
