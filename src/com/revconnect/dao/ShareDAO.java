package com.revconnect.dao;

public interface ShareDAO {

    boolean sharePost(int postId, int userId);

    int getShareCount(int postId);
}
