package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.dao.impl.MessageDAOImpl;
import com.revconnect.model.Message;

public class MessageService {

    private MessageDAO dao;
    private NotificationService notificationService;

    public MessageService() {
        dao = new MessageDAOImpl();
        notificationService = new NotificationService();
    }

    public boolean sendMessage(Message msg) {

        boolean success = dao.sendMessage(msg);

        if (success) {
            if (msg.getReceiverId() != msg.getSenderId()) {
                notificationService.notifyUser(
                    msg.getReceiverId(),
                    "New message received"
                );
            }
        }

        return success;
    }

    public List<Message> getInbox(int userId) {
        return dao.getInbox(userId);
    }

    public List<Message> getConversation(int user1, int user2) {
        return dao.getConversation(user1, user2);
    }
}
