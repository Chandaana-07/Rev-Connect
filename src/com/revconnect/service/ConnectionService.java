package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.UserConnection;

public class ConnectionService {

    private static List<UserConnection> connections = new ArrayList<UserConnection>();

    // ========== CONNECTIONS ==========

    // 1. Send Request
    public void sendRequest(int fromUser, int toUser) {
        connections.add(new UserConnection(fromUser, toUser, "PENDING"));
        System.out.println("Connection request sent.");
    }

    // 2. View Pending Requests
    public List<UserConnection> getPending(int userId) {
        List<UserConnection> list = new ArrayList<UserConnection>();
        for (UserConnection c : connections) {
            if (c.getReceiverId() == userId &&
                c.getStatus().equals("PENDING")) {
                list.add(c);
            }
        }
        return list;
    }

    // 3. Accept Request
    public void acceptRequest(int fromUser, int toUser) {
        for (UserConnection c : connections) {
            if (c.getSenderId() == fromUser &&
                c.getReceiverId() == toUser &&
                c.getStatus().equals("PENDING")) {

                c.setStatus("ACCEPTED");
                System.out.println("Connection accepted.");
                return;
            }
        }
        System.out.println("Request not found.");
    }

    // 4. Reject Request
    public void rejectRequest(int fromUser, int toUser) {
        for (UserConnection c : connections) {
            if (c.getSenderId() == fromUser &&
                c.getReceiverId() == toUser &&
                c.getStatus().equals("PENDING")) {

                connections.remove(c);
                System.out.println("Request rejected.");
                return;
            }
        }
        System.out.println("Request not found.");
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

    // 6. Remove Connection
    public void removeConnection(int user1, int user2) {
        for (UserConnection c : connections) {
            if ((c.getSenderId() == user1 && c.getReceiverId() == user2) ||
                (c.getSenderId() == user2 && c.getReceiverId() == user1)) {

                connections.remove(c);
                System.out.println("Connection removed.");
                return;
            }
        }
        System.out.println("Connection not found.");
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
