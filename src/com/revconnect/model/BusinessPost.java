package com.revconnect.model;

import java.io.Serializable;
import java.util.Date;

public class BusinessPost implements Serializable {

    private String businessName;
    private String content;
    private Date timestamp;

    public BusinessPost(String businessName, String content) {
        this.businessName = businessName;
        this.content = content;
        this.timestamp = new Date();
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Business: " + businessName +
               "\nPost: " + content +
               "\nTime: " + timestamp +
               "\n----------------------";
    }
}
