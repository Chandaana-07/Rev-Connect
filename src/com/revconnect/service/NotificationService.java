package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.dao.impl.NotificationDAOImpl;
import com.revconnect.model.Notification;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
public class NotificationService {

    private NotificationDAO dao;
    private ConnectionProvider connectionProvider;

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
    public boolean markAsRead(int notifId, int userId) {
       return dao.markAsRead(notifId, userId);
    }

    // Mark all as read
    public void markAllRead(int userId) {
        List<Notification> list = getMyNotifications(userId);
        for (Notification n : list) {
            markAsRead(n.getNotificationId(), userId);
        }
    }
 // Normal Constructor
    public NotificationService() {
        this.dao = new NotificationDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
    }
   // TEST CONSTRUCTOR (Mockito uses this)
    public NotificationService(
            NotificationDAO dao,
            ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
    }
    public boolean createNotification(Notification n) {
        return dao.createNotification(n);
    }

}
