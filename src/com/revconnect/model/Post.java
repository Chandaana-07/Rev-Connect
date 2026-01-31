package com.revconnect.model;

import java.util.Date;

public class Post {

    private int postId;
    private int userId;
    private String postName;

    private String username;
    private String content;
    private java.sql.Timestamp createdAt;
    private String postType;       
    private String ctaText;     
    private int taggedProductId;
    private java.sql.Timestamp scheduledTime;
    private int reach;


    
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
    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getCtaText() {
        return ctaText;
    }
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


    public void setCtaText(String ctaText) {
        this.ctaText = ctaText;
    }

    public int getTaggedProductId() {
        return taggedProductId;
    }

    public void setTaggedProductId(int taggedProductId) {
        this.taggedProductId = taggedProductId;
    }

    public java.sql.Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(java.sql.Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

}
