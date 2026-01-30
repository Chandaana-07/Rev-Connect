package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.model.Post;

public class PostService {

    private PostDAO dao;

    public PostService() {
        dao = new PostDAOImpl();
    }

    // ---------------- CREATE ----------------
    public boolean createPost(Post post) {
        return dao.createPost(post);
    }

    // ---------------- VIEW MY POSTS ----------------
    public List<Post> getPostsByUser(int userId) {
        return dao.getPostsByUser(userId);
    }

    // ---------------- GLOBAL FEED ----------------
    public List<Post> getAllPosts() {
        return dao.getAllPosts();
    }

    // ---------------- CONNECTION FEED ----------------
    public List<Post> getFeedPosts(int userId) {
        return dao.getFeedPosts(userId);
    }

    // ---------------- EDIT ----------------
    public boolean updatePost(int postId, int userId, String content) {
        return dao.updatePost(postId, userId, content);
    }

    // ---------------- DELETE ----------------
    public boolean deletePost(int postId, int userId) {
        return dao.deletePost(postId, userId);
    }

    // ---------------- OWNER CHECK ----------------
    public int getPostOwnerId(int postId) {
        return dao.getPostOwnerId(postId);
    }
   
    public List<Post> searchByHashtag(String tag) {
        return dao.searchByHashtag(tag);
    }
    public List<String> getTrendingHashtags() {
        return dao.getTrendingHashtags();
    }
  
    public List<Post> getFilteredFeed(int userId, String type) {

        List<Post> posts = getFeedPosts(userId);
        List<Post> filtered = new java.util.ArrayList<Post>();

        if (type.equals("MY_POSTS")) {
            for (Post p : posts) {
                if (p.getUserId() == userId) {
                    filtered.add(p);
                }
            }
            return filtered;
        }

        if (type.equals("CREATORS")) {
            return posts;
        }

        return posts;
    }

    public boolean editPost(int postId, int userId, String newContent) {

        List<Post> posts = getPostsByUser(userId);

        for (Post p : posts) {
            if (p.getPostId() == postId) {
                p.setContent(newContent);
                return true;
            }
        }
        return false;
    }
 // ===================== PROMOTIONAL POST =====================
    public boolean createPromoPost(Post post) {
        post.setPostType("PROMO");
        return dao.createPost(post);
    }

    // ===================== POST ANALYTICS =====================
    public Post getPostAnalytics(int postId) {
        return dao.getPostById(postId);
    }

}
