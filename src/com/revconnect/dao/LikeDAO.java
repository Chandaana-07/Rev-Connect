package com.revconnect.dao;

public interface LikeDAO {

    boolean likePost(int postId, int userId);

    boolean unlikePost(int postId, int userId);

    int getLikeCount(int postId);

    boolean hasUserLiked(int postId, int userId);
}
