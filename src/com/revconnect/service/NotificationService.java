package com.revconnect.service;

import com.revconnect.model.Notification;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final String FILE = "notifications.dat";

    // Save new notification
    public void addNotification(Notification n) {
        List<Notification> list = getAll();
        list.add(n);
        saveAll(list);
    }

    // Get all notifications
    private List<Notification> getAll() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE));
            return (List<Notification>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<Notification>();
        }
    }

    // Get user's notifications
    public List<Notification> getUserNotifications(int userId) {
        List<Notification> result = new ArrayList<Notification>();
        for (Notification n : getAll()) {
            if (n.getUserId() == userId) {
                result.add(n);
            }
        }
        return result;
    }

    // Unread count
    public int getUnreadCount(int userId) {
        int count = 0;
        for (Notification n : getUserNotifications(userId)) {
            if (!n.isRead()) {
                count++;
            }
        }
        return count;
    }

    // Mark all as read
    public void markAllRead(int userId) {
        List<Notification> list = getAll();
        for (Notification n : list) {
            if (n.getUserId() == userId) {
                n.markRead();
            }
        }
        saveAll(list);
    }

    private void saveAll(List<Notification> list) {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE));
            oos.writeObject(list);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving notifications");
        }
    }
}
