package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.model.Message;

public class MessageDAOImpl implements MessageDAO {

    // SEND MESSAGE
    @Override
    public boolean sendMessage(Connection con, int senderId, int receiverId, String content) {
        try {
            String sql =
                "INSERT INTO MESSAGES (SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT, IS_READ) " +
                "VALUES (?, ?, ?, SYSTIMESTAMP, 'N')";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // INBOX
    @Override
    public List<Message> getInbox(Connection con, int userId) {

        List<Message> list = new ArrayList<Message>();

        try {
            String sql =
                "SELECT MESSAGE_ID, SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT " +
                "FROM MESSAGES " +
                "WHERE RECEIVER_ID = ? " +
                "ORDER BY CREATED_AT DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

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
        }

        return list;
    }

    // CONVERSATION
    @Override
    public List<Message> getConversation(Connection con, int user1, int user2) {

        List<Message> list = new ArrayList<Message>();

        try {
            String sql =
                "SELECT MESSAGE_ID, SENDER_ID, RECEIVER_ID, CONTENT, CREATED_AT " +
                "FROM MESSAGES " +
                "WHERE (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "   OR (SENDER_ID = ? AND RECEIVER_ID = ?) " +
                "ORDER BY CREATED_AT";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.setInt(3, user2);
            ps.setInt(4, user1);

            ResultSet rs = ps.executeQuery();

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
        }

        return list;
    }

    // MARK READ
    @Override
    public void markRead(Connection con, int userId) {
        try {
            String sql =
                "UPDATE MESSAGES SET IS_READ = 'Y' WHERE RECEIVER_ID = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
