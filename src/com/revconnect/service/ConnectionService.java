package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.ConnectionDAO;
import com.revconnect.dao.impl.ConnectionDAOImpl;
import com.revconnect.model.UserConnection;

public class ConnectionService {

    private ConnectionDAO connectionDAO;

    // ================= CONSTRUCTOR =================
    public ConnectionService() {
        this.connectionDAO = new ConnectionDAOImpl();
    }

    // ========== CONNECTION REQUEST SYSTEM ==========

    // 1. Send Request
    public boolean sendRequest(int senderId, int receiverId, String senderName, String receiverName) {
        return connectionDAO.sendRequest(senderId, receiverId, senderName, receiverName);
    }

    // 2. View Pending Requests
    public List<UserConnection> getPendingRequests(int userId) {
        return connectionDAO.getPendingRequests(userId);
    }

    // 3. Accept Request
    public boolean acceptRequest(int connectionId, int receiverId) {
        return connectionDAO.acceptRequest(connectionId, receiverId);
    }

    // 4. Reject Request
    public boolean rejectRequest(int connectionId, int receiverId) {
        return connectionDAO.rejectRequest(connectionId, receiverId);
    }

    // 5. View My Connections
    public List<UserConnection> getConnections(int userId) {
        return connectionDAO.getConnections(userId);
    }

    // 6. Remove Connection
    public boolean removeConnection(int connectionId, int userId) {
        return connectionDAO.removeConnection(connectionId, userId);
    }

    // ========== FOLLOW SYSTEM (DB VERSION) ==========

    // Follow User
    public boolean follow(int fromUser, int toUser) {
        return connectionDAO.followUser(fromUser, toUser);
    }

    // Unfollow User
    public boolean unfollow(int fromUser, int toUser) {
        return connectionDAO.unfollowUser(fromUser, toUser);
    }

    // View Followers
    public List<UserConnection> getFollowers(int userId) {
        return connectionDAO.getFollowers(userId);
    }

    // View Following
    public List<UserConnection> getFollowing(int userId) {
        return connectionDAO.getFollowing(userId);
    }

    public int getFollowerCount(int userId) {
        return connectionDAO.getFollowerCount(userId);
    }
    
}
