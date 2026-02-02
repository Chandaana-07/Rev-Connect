package com.revconnect.service;

import com.revconnect.dao.LikeDAO;
import com.revconnect.dao.impl.LikeDAOImpl;

import com.revconnect.service.NotificationService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import org.apache.log4j.Logger;

public class LikeService {

    private LikeDAO dao = new LikeDAOImpl();
    Logger logger =
            Logger.getLogger(LikeService.class);

    // NEW: Services for notifications
    private NotificationService notificationService = new NotificationService();
    private PostService postService = new PostService();
    private UserService userService = new UserService();
    
    public LikeService() {
        this.dao = new LikeDAOImpl();
        this.postService = new PostService();
        this.userService = new UserService();
        this.notificationService = new NotificationService();
        logger.info("LikeService initialized (Default Constructor)");
    }


    public void likePost(int userId, int postId) {
    	logger.info("Like attempt | User ID: "
                + userId + " | Post ID: " + postId);

        boolean success = dao.likePost(userId, postId);

        if (success) {
            System.out.println("Post liked!");
            logger.info("Post liked successfully | User ID: "
                    + userId + " | Post ID: " + postId);

            int ownerId = postService.getPostOwner(postId);

            if (ownerId != userId) {
                String username = userService.getUsernameById(userId);

                notificationService.notifyUser(
                    ownerId,
                    "@" + username + " liked your post (Post ID: " + postId + ")"
                );
                logger.info("Like notification sent | From User ID: "
                        + userId
                        + " | To User ID: "
                        + ownerId);
            }
        }
    }
     // For Mockito testing
        public LikeService(LikeDAO dao) {
            this.dao = dao;
        }

    

    public int getLikeCount(int postId) {
    	logger.info("Fetching like count | Post ID: "
                + postId);

        return dao.getLikeCount(postId);
    }

    public boolean unlikePost(int userId, int postId) {
    	 logger.info("Unlike attempt | User ID: "
                 + userId
                 + " | Post ID: "
                 + postId);
        return dao.unlikePost(userId, postId);
    }
}
