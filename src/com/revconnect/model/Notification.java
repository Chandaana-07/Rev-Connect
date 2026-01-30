package com.revconnect.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {

    private int notificationId;
    private int userId;
    private String type;
    private String message;
    private boolean isRead;
    private Timestamp createdAt;

    // REQUIRED: Empty constructor
    public Notification() {
    }

    // Optional constructor
    public Notification(int userId, String type, String message) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.isRead = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // -------- GETTERS --------
    public int getNotificationId() {
        return notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // -------- SETTERS --------
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message +
               (isRead ? " (Read)" : " (Unread)");
    }
}
