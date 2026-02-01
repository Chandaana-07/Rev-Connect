package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.UserConnection;

public interface ConnectionDAO {

    // ========== REQUEST SYSTEM ==========
    boolean sendRequest(int senderId, int receiverId, String senderName, String receiverName);

    List<UserConnection> getPendingRequests(int userId);

    boolean acceptRequest(int connectionId, int receiverId);

    boolean rejectRequest(int connectionId, int receiverId);

    List<UserConnection> getConnections(int userId);

    boolean removeConnection(int connectionId, int userId);

    // ========== FOLLOW SYSTEM ==========
    boolean followUser(int fromUser, int toUser);

    boolean unfollowUser(int fromUser, int toUser);

    List<UserConnection> getFollowers(int userId);

    List<UserConnection> getFollowing(int userId);

    int getFollowerCount(int userId);

    // ========== EXTRA HELPERS ==========
    int getSenderIdByConnection(int connectionId);

    boolean removeConnectionByUser(int connectionId, int userId);
}
