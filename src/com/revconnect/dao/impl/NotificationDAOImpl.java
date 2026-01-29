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

    @Override
    public boolean addNotification(int userId, String message) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO NOTIFICATIONS (USER_ID, MESSAGE) VALUES (?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, message);

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
    public List<Notification> getNotifications(int userId) {

        List<Notification> list = new ArrayList<Notification>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT NOTIF_ID, MESSAGE, IS_READ, CREATED_AT " +
                "FROM NOTIFICATIONS WHERE USER_ID = ? " +
                "ORDER BY NOTIF_ID DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotifId(rs.getInt("NOTIF_ID"));
                n.setMessage(rs.getString("MESSAGE"));
                n.setIsRead(rs.getInt("IS_READ"));
                n.setCreatedAt(rs.getString("CREATED_AT"));

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

    @Override
    public boolean markAsRead(int notifId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE NOTIFICATIONS SET IS_READ = 1 " +
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
}
