package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.dao.impl.NotificationDAOImpl;
import com.revconnect.model.Notification;

public class NotificationService {

    private NotificationDAO dao;

    public NotificationService() {
        dao = new NotificationDAOImpl();
    }

    // Create notification
    public boolean createNotification(Notification n) {
        return dao.createNotification(n);
    }

    // Helper to notify user
    public void notifyUser(int userId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        dao.createNotification(n);
    }

    // View my notifications
    public List<Notification> getMyNotifications(int userId) {
        return dao.getMyNotifications(userId);
    }

    // Mark as read
    public boolean markAsRead(int notifId, int userId) {
        return dao.markAsRead(notifId, userId);
    }

    // Unread count
    public int getUnreadCount(int userId) {
        return dao.getUnreadCount(userId);
    }
}
