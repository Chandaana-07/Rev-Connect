package com.revconnect.service;

import com.revconnect.dao.LikeDAO;
import com.revconnect.dao.impl.LikeDAOImpl;

public class LikeService {

    private LikeDAO dao = new LikeDAOImpl();

    public void likePost(int userId, int postId) {
        boolean success = dao.likePost(userId, postId);
        if (success) {
            System.out.println("Post liked!");
        }
    }

    public int getLikeCount(int postId) {
        return dao.getLikeCount(postId);
    }

    public boolean unlikePost(int userId, int postId) {
        return dao.unlikePost(userId, postId);
    }
}
