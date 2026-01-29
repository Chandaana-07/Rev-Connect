package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Message;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public boolean sendMessage(Message message) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO MESSAGES (SENDER_ID, RECEIVER_ID, CONTENT) " +
                "VALUES (?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, message.getSenderId());
            ps.setInt(2, message.getReceiverId());
            ps.setString(3, message.getContent());

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
    public List<Message> getInbox(int userId) {

        List<Message> messages = new ArrayList<Message>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT m.MSG_ID, m.SENDER_ID, m.CONTENT, m.SENT_AT, u.USERNAME " +
                "FROM MESSAGES m " +
                "JOIN USERS u ON m.SENDER_ID = u.USER_ID " +
                "WHERE m.RECEIVER_ID = ? " +
                "ORDER BY m.SENT_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setMsgId(rs.getInt("MSG_ID"));
                msg.setSenderId(rs.getInt("SENDER_ID"));
                msg.setContent(rs.getString("CONTENT"));
                msg.setSentAt(rs.getTimestamp("SENT_AT"));

                messages.add(msg);
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

        return messages;
    }

    @Override
    public List<Message> getConversation(int user1Id, int user2Id) {

        List<Message> messages = new ArrayList<Message>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT MSG_ID, SENDER_ID, RECEIVER_ID, CONTENT, SENT_AT " +
                "FROM MESSAGES " +
                "WHERE (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "   OR (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "ORDER BY SENT_AT";

            ps = con.prepareStatement(sql);
            ps.setInt(1, user1Id);
            ps.setInt(2, user2Id);
            ps.setInt(3, user2Id);
            ps.setInt(4, user1Id);

            rs = ps.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setMsgId(rs.getInt("MSG_ID"));
                msg.setSenderId(rs.getInt("SENDER_ID"));
                msg.setReceiverId(rs.getInt("RECEIVER_ID"));
                msg.setContent(rs.getString("CONTENT"));
                msg.setSentAt(rs.getTimestamp("SENT_AT"));

                messages.add(msg);
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

        return messages;
    }
}