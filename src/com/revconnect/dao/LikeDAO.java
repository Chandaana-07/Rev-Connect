package com.revconnect.dao;
import java.sql.Connection;

public interface LikeDAO {

    boolean likePost(int postId, int userId);

    boolean unlikePost(int postId, int userId);

    int getLikeCount(int postId);

    boolean hasUserLiked(Connection con, int postId, int userId);
}
