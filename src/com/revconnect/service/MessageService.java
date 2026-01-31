package com.revconnect.service;

import java.sql.Connection;
import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.dao.impl.MessageDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Message;

public class MessageService {

    private MessageDAO dao = new MessageDAOImpl();

    // SEND MESSAGE
    public boolean send(Message m) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.sendMessage(
                con,
                m.getSenderId(),
                m.getReceiverId(),
                m.getContent()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {}
        }
        return false;
    }

    // GET CONVERSATION
    public List<Message> getConversation(int user1, int user2) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getConversation(con, user1, user2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {}
        }
        return null;
    }

    // MARK READ
    public void markRead(int userId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            dao.markRead(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {}
        }
    }

    // INBOX
    public List<Message> getInbox(int userId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getInbox(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {}
        }
        return null;
    }
    public boolean sendMessage(int senderId, int receiverId, String content) {
        return send(new com.revconnect.model.Message(senderId, receiverId, content));
    }

}
