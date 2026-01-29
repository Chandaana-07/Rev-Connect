package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Notification;

public interface NotificationDAO {

    boolean addNotification(int userId, String message);

    List<Notification> getNotifications(int userId);

    boolean markAsRead(int notifId, int userId);
}
