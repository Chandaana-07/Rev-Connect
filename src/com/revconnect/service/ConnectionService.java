package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.ConnectionDAO;
import com.revconnect.dao.impl.ConnectionDAOImpl;
import com.revconnect.model.UserConnection;
import com.revconnect.service.NotificationService;


public class ConnectionService {

    private ConnectionDAO dao;
    private NotificationService notificationService = new NotificationService();

    public ConnectionService() {
        dao = new ConnectionDAOImpl();
        notificationService = new NotificationService(); // ADD THIS
    }

    // ---------------- SEND REQUEST ----------------
    public boolean sendRequest(int senderId, int receiverId) {

        boolean success = dao.sendRequest(senderId, receiverId);

        if (success) {
            notificationService.notifyUser(
                receiverId,
                "You received a new connection request"
            );
        }

        return success;
    }


    // ---------------- ACCEPT REQUEST ----------------
    public boolean acceptRequest(int connectionId, int receiverId) {

        int senderId = dao.getSenderIdByConnection(connectionId);

        boolean success = dao.acceptRequest(connectionId, receiverId);

        if (success) {
            notificationService.notifyUser(
                senderId,
                "Your connection request was accepted"
            );
        }

        return success;
    }


    // ---------------- REJECT REQUEST ----------------
    public boolean rejectRequest(int connectionId, int receiverId) {
        return dao.rejectRequest(connectionId, receiverId);
    }

    // ---------------- VIEW PENDING ----------------
    public List<UserConnection> getPendingRequests(int userId) {
        return dao.getPendingRequests(userId);
    }

    // ---------------- VIEW CONNECTIONS ----------------
    public List<UserConnection> getConnections(int userId) {
        return dao.getConnections(userId);
    }

    // ---------------- REMOVE CONNECTION ----------------
    public boolean removeConnectionByUser(int myUserId, int otherUserId) {
        return dao.removeConnectionByUser(myUserId, otherUserId);
    }
}
