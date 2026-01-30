package com.revconnect.model;

import java.sql.Timestamp;

public class UserConnection {

    // ===== Fields =====
    private int connectionId;
    private int senderId;
    private int receiverId;
    private String status; // PENDING, ACCEPTED, FOLLOWING
    private Timestamp createdAt;

    // ===== Default Constructor (REQUIRED by DAO) =====
    public UserConnection() {
    }

    // ===== Parameterized Constructor =====
    public UserConnection(int senderId, int receiverId, String status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    // ===== Getters & Setters =====
    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
