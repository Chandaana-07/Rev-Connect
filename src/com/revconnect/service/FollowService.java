package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.FollowDAO;
import com.revconnect.dao.impl.FollowDAOImpl;
import com.revconnect.model.Follow;
import com.revconnect.service.NotificationService;

public class FollowService {

    private FollowDAO dao;
    private NotificationService notificationService;

    public FollowService() {
        dao = new FollowDAOImpl();
        notificationService = new NotificationService();
    }

    // ---------------- FOLLOW USER ----------------
    public boolean followUser(int followerId, int followingId) {

        boolean success = dao.followUser(followerId, followingId);

        if (success) {
            notificationService.notifyUser(
                followingId,
                "You have a new follower"
            );
        }

        return success;
    }

   
    public boolean unfollowUser(int followerId, int followingId) {
        return dao.unfollowUser(followerId, followingId);
    }

   
    public List<Follow> getFollowers(int userId) {
        return dao.getFollowers(userId);
    }

    
    public List<Follow> getFollowing(int userId) {
        return dao.getFollowing(userId);
    }

    
    public boolean isFollowing(int followerId, int followingId) {
        return dao.isFollowing(followerId, followingId);
    }
    public void notifyUser(int userId, String message) {
        // Stub method to avoid compile error
    }

}
