package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Notification;

public interface NotificationDAO {

    // Create a new notification
    boolean createNotification(Notification n);

    // Get all notifications for a user
    List<Notification> getMyNotifications(int userId);

    // Mark notification as read
    boolean markAsRead(int notifId, int userId);

    // Get unread count
    int getUnreadCount(int userId);

    

}
