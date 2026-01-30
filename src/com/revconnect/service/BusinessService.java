package com.revconnect.service;

import com.revconnect.model.BusinessPage;
import com.revconnect.model.BusinessPost;
import com.revconnect.model.FollowerMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessService {

    private final String BUSINESS_FILE = "business_pages.dat";
    private final String POST_FILE = "business_posts.dat";
    private final String MESSAGE_FILE = "follower_messages.dat";

    // ---------------- BUSINESS ----------------

    public void saveBusiness(BusinessPage page) {
        try {
            List<BusinessPage> list = getAllBusinesses();
            list.add(page);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUSINESS_FILE));
            oos.writeObject(list);
            oos.close();

            System.out.println("Business Page Created Successfully!");
        } catch (Exception e) {
            System.out.println("Error saving business page");
        }
    }

    public List<BusinessPage> getAllBusinesses() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BUSINESS_FILE));
            return (List<BusinessPage>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<BusinessPage>();
        }
    }

    public boolean hasBusiness() {
        return !getAllBusinesses().isEmpty();
    }

    // ---------------- POSTS ----------------

    public void savePost(BusinessPost post) {
        try {
            List<BusinessPost> list = getAllPosts();
            list.add(post);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(POST_FILE));
            oos.writeObject(list);
            oos.close();

            System.out.println("Post Published Successfully!");
        } catch (Exception e) {
            System.out.println("Error saving post");
        }
    }

    public List<BusinessPost> getAllPosts() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(POST_FILE));
            return (List<BusinessPost>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<BusinessPost>();
        }
    }

    // ---------------- FOLLOWER MESSAGES ----------------

    public void saveMessage(FollowerMessage msg) {
        try {
            List<FollowerMessage> list = getAllMessages();
            list.add(msg);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MESSAGE_FILE));
            oos.writeObject(list);
            oos.close();

            System.out.println("Message Sent Successfully!");
        } catch (Exception e) {
            System.out.println("Error saving message");
        }
    }

    public List<FollowerMessage> getAllMessages() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MESSAGE_FILE));
            return (List<FollowerMessage>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<FollowerMessage>();
        }
    }
}
