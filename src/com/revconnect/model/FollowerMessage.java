package com.revconnect.model;

import java.io.Serializable;

public class FollowerMessage implements Serializable {

    private String followerName;
    private String message;

    public FollowerMessage(String followerName, String message) {
        this.followerName = followerName;
        this.message = message;
    }

    public String getFollowerName() {
        return followerName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Follower: " + followerName +
               "\nMessage: " + message;
    }
}
