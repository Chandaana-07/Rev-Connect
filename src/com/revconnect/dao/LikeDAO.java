package com.revconnect.dao;

public interface LikeDAO {

    boolean likePost(int userId, int postId);

    boolean unlikePost(int userId, int postId);

    int getLikeCount(int postId);

    boolean hasUserLiked(int userId, int postId);
}
