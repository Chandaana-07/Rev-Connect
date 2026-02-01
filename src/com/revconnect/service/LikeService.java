package com.revconnect.service;

import com.revconnect.dao.LikeDAO;
import com.revconnect.dao.impl.LikeDAOImpl;

import com.revconnect.service.NotificationService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;

public class LikeService {

    private LikeDAO dao = new LikeDAOImpl();

    // NEW: Services for notifications
    private NotificationService notificationService = new NotificationService();
    private PostService postService = new PostService();
    private UserService userService = new UserService();

    public void likePost(int userId, int postId) {

        boolean success = dao.likePost(userId, postId);

        if (success) {
            System.out.println("Post liked!");

            int ownerId = postService.getPostOwner(postId);

            if (ownerId != userId) {
                String username = userService.getUsernameById(userId);

                notificationService.notifyUser(
                    ownerId,
                    "@" + username + " liked your post (Post ID: " + postId + ")"
                );
            }
        }
    }

    public int getLikeCount(int postId) {
        return dao.getLikeCount(postId);
    }

    public boolean unlikePost(int userId, int postId) {
        return dao.unlikePost(userId, postId);
    }
}
