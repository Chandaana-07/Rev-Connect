package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.dao.impl.NotificationDAOImpl;
import com.revconnect.model.Notification;

public class NotificationService {

    private NotificationDAO dao = new NotificationDAOImpl();

    public boolean notifyUser(int userId, String message) {
        return dao.addNotification(userId, message);
    }

    public List<Notification> getMyNotifications(int userId) {
        return dao.getNotifications(userId);
    }

    public boolean markAsRead(int notifId, int userId) {
        return dao.markAsRead(notifId, userId);
    }
}
