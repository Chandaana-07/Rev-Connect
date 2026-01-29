package com.revconnect.service;

import com.revconnect.dao.LikeDAO;
import com.revconnect.dao.impl.LikeDAOImpl;

public class LikeService {

    private LikeDAO likeDAO;

    public LikeService() {
        likeDAO = new LikeDAOImpl();
    }

    public boolean likePost(int postId, int userId) {
        return likeDAO.likePost(postId, userId);
    }

    public boolean unlikePost(int postId, int userId) {
        return likeDAO.unlikePost(postId, userId);
    }

    public int getLikeCount(int postId) {
        return likeDAO.getLikeCount(postId);
    }

    public boolean hasUserLiked(int postId, int userId) {
        return likeDAO.hasUserLiked(postId, userId);
    }
}
