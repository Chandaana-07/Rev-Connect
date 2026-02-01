package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.dao.impl.NotificationDAOImpl;
import com.revconnect.model.Notification;

public class NotificationService {

    private NotificationDAO dao = new NotificationDAOImpl();

    // Create notification
    public void notifyUser(int userId, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        dao.createNotification(n);
    }

    // Get all my notifications
    public List<Notification> getMyNotifications(int userId) {
        return dao.getMyNotifications(userId);
    }

    // Unread count
    public int getUnreadCount(int userId) {
        return dao.getUnreadCount(userId);
    }

    // Mark single as read
    public void markRead(int notifId, int userId) {
        dao.markAsRead(notifId, userId);
    }

    // Mark all as read
    public void markAllRead(int userId) {
        List<Notification> list = getMyNotifications(userId);
        for (Notification n : list) {
            dao.markAsRead(n.getNotificationId(), userId);
        }
    }
}
