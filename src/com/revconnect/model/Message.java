package com.revconnect.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {

    private int msgId;
    private int senderId;
    private int receiverId;
    private String content;
    private boolean isRead;
    private Timestamp sentAt;

    // REQUIRED: Empty constructor (DAO uses Message())
    public Message() {
    }

    // Optional constructor
    public Message(int senderId, int receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.isRead = false;
        this.sentAt = new Timestamp(System.currentTimeMillis());
    }

    // -------- GETTERS --------
    public int getMsgId() {
        return msgId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    // -------- SETTERS --------
    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return "From: " + senderId +
               " | To: " + receiverId +
               " | " + content +
               (isRead ? " (Read)" : " (Unread)");
    }
}
