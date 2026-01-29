package com.revconnect.model;

import java.util.Date;

public class Post {

    private int postId;
    private int userId;
    private String username;
    private String content;
    private java.sql.Timestamp createdAt;

    
    public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public java.sql.Timestamp getCreatedAt() {
	    return createdAt;
	}

	public void setCreatedAt(java.sql.Timestamp createdAt) {
	    this.createdAt = createdAt;
	}

	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
