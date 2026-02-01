package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.model.Message;

public class MessageDAOImpl implements MessageDAO {

    // ---------------- SEND MESSAGE ----------------
    @Override
    public boolean sendMessage(Connection con, int senderId, int receiverId, String content) {

        PreparedStatement ps = null;

        try {
            String sql =
                "INSERT INTO MESSAGES (SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT) " +
                "VALUES (?, ?, ?, SYSTIMESTAMP)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // ---------------- INBOX ----------------
    @Override
    public List<Message> getInbox(Connection con, int userId) {

        List<Message> list = new ArrayList<Message>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT MESSAGE_ID, SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT " +
                "FROM MESSAGES " +
                "WHERE RECEIVER_ID = ? " +
                "ORDER BY CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Message m = new Message();
                m.setMessageId(rs.getInt("MESSAGE_ID"));
                m.setSenderId(rs.getInt("SENDER_ID"));
                m.setReceiverId(rs.getInt("RECEIVER_ID"));
                m.setContent(rs.getString("CONTENT"));
                m.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(m);
            }

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

        return list;
    }

    // ---------------- CONVERSATION ----------------
    @Override
    public List<Message> getConversation(Connection con, int user1, int user2) {

        List<Message> list = new ArrayList<Message>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT MESSAGE_ID, SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT " +
                "FROM MESSAGES " +
                "WHERE (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "   OR (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "ORDER BY CREATED_AT";

            ps = con.prepareStatement(sql);
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);

            rs = ps.executeQuery();

            while (rs.next()) {
                Message m = new Message();
                m.setMessageId(rs.getInt("MESSAGE_ID"));
                m.setSenderId(rs.getInt("SENDER_ID"));
                m.setReceiverId(rs.getInt("RECEIVER_ID"));
                m.setContent(rs.getString("CONTENT"));
                m.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(m);
            }

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

        return list;
    }

    // ---------------- MARK READ (DISABLED SAFELY) ----------------
    @Override
    public void markRead(Connection con, int userId) {
        // Disabled because IS_READ column does not exist in Oracle 10g table
    }
}
