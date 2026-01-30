package com.revconnect.model;

public class PinnedPost {
    private int userId;
    private int postId;

    public PinnedPost(int userId, int postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public int getUserId() { return userId; }
    public int getPostId() { return postId; }
}

