package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Notification;

public interface NotificationDAO {

    boolean createNotification(Notification n);

    List<Notification> getMyNotifications(int userId);

    boolean markAsRead(int notifId, int userId);

    int getUnreadCount(int userId);
}
