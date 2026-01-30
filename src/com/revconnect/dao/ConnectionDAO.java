package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.UserConnection;

public interface ConnectionDAO {

    // Send a connection request
    boolean sendRequest(int senderId, int receiverId);

    // Accept a request
    boolean acceptRequest(int connectionId, int receiverId);

    // Reject a request
    boolean rejectRequest(int connectionId, int receiverId);

    // View pending requests for a user
    List<UserConnection> getPendingRequests(int userId);

    // View accepted connections
    List<UserConnection> getConnections(int userId);
    
    boolean removeConnection(int connectionId, int userId);
    
    boolean removeConnectionByUser(int myUserId, int otherUserId);

    int getSenderIdByConnection(int connectionId);


}
