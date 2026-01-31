package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.UserConnection;
import com.revconnect.dao.ConnectionDAO;
import com.revconnect.dao.impl.ConnectionDAOImpl;

public class ConnectionService {
	private ConnectionDAO connectionDAO=new ConnectionDAOImpl();

    private static List<UserConnection> connections = new ArrayList<UserConnection>();

    // ========== CONNECTIONS ==========

    // 1. Send Request
    
 // 1. Send Request (DB version)
    public boolean sendRequest(int senderId,int receiverId,String senderName, String receiverName) {
        return connectionDAO.sendRequest(senderId,receiverId,senderName, receiverName);
    }
    
    // 2. View Pending Requests
    public List<UserConnection> getPendingRequests(int userId) {
        return connectionDAO.getPendingRequests(userId);
    }

 // 3. Accept Request (DB version)
    public boolean acceptRequest(int connectionId, int receiverId) {
        return connectionDAO.acceptRequest(connectionId, receiverId);
    }

 // 4. Reject Request (DB version)
    public boolean rejectRequest(int connectionId, int receiverId) {
        return connectionDAO.rejectRequest(connectionId, receiverId);
    }
  

    // 5. View My Connections
    public List<UserConnection> getConnections(int userId) {
        List<UserConnection> list = new ArrayList<UserConnection>();
        for (UserConnection c : connections) {
            if ((c.getSenderId() == userId || c.getReceiverId() == userId)
                && c.getStatus().equals("ACCEPTED")) {

                list.add(c);
            }
        }
        return list;
    }

 // 6. Remove Connection (DB version)
    public boolean removeConnection(int connectionId, int userId) {
        return connectionDAO.removeConnection(connectionId, userId);
    }
    // ========== FOLLOW SYSTEM ==========

    // Follow User
    public void follow(int fromUser, int toUser) {
        connections.add(new UserConnection(fromUser, toUser, "FOLLOWING"));     // call the followUser method in followDAOImpl
        System.out.println("Now following user.");
    }

    // Unfollow User
    public void unfollow(int fromUser, int toUser) {
        for (UserConnection c : connections) {
            if (c.getSenderId() == fromUser &&
                c.getReceiverId() == toUser &&
                c.getStatus().equals("FOLLOWING")) {

                connections.remove(c);
                System.out.println("Unfollowed successfully.");
                return;
            }
        }
        System.out.println("You are not following this user.");
    }

    // View Followers
    public List<UserConnection> getFollowers(int userId) {
        List<UserConnection> list = new ArrayList<UserConnection>();
        for (UserConnection c : connections) {
            if (c.getReceiverId() == userId &&
                c.getStatus().equals("FOLLOWING")) {

                list.add(c);
            }
        }
        return list;
    }

    // View Following
    public List<UserConnection> getFollowing(int userId) {
        List<UserConnection> list = new ArrayList<UserConnection>();
        for (UserConnection c : connections) {
            if (c.getSenderId() == userId &&
                c.getStatus().equals("FOLLOWING")) {

                list.add(c);
            }
        }
        return list;
    }
    public int getFollowerCount(int userId) {
        return getFollowers(userId).size();
    }

}
