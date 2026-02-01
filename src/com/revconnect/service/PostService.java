package com.revconnect.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Post;

public class PostService {

    private PostDAO dao;
    private ConnectionProvider connectionProvider;

    // ================= DEFAULT CONSTRUCTOR =================
    public PostService() {
        this.dao = new PostDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
    }

    // ================= TEST CONSTRUCTOR =================
    public PostService(PostDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
    }

    // ---------------- CREATE ----------------
    public boolean createPost(int userId, String postName, String content, String postType) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.createPost(con, userId, postName, content, postType);
        } catch (Exception e) {
            System.out.println("Error creating post.");
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- VIEW MY POSTS ----------------
    public List<Post> getPostsByUser(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getPostsByUser(con, userId);
        } catch (Exception e) {
            System.out.println("Error fetching posts.");
            e.printStackTrace();
        }
        return new ArrayList<Post>();
    }

    // ---------------- GLOBAL FEED ----------------
    public List<Post> getAllPosts() {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getAllPosts(con);
        } catch (Exception e) {
            System.out.println("Error loading global feed.");
            e.printStackTrace();
        }
        return new ArrayList<Post>();
    }

    // ---------------- CONNECTION FEED ----------------
    public List<Post> getFeedPosts(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getFeedPosts(con, userId);
        } catch (Exception e) {
            System.out.println("Error loading feed.");
            e.printStackTrace();
        }
        return new ArrayList<Post>();
    }

    // ---------------- EDIT BY POST NAME ----------------
    public boolean updatePostByName(int userId, String postName, String newContent) {
        try {
            Connection con = DBConnection.getConnection();

            int postId = dao.getPostIdByName(con, postName, userId);
            if (postId == -1) {
                System.out.println("Post not found!");
                return false;
            }

            return dao.updatePost(con, postId, userId, newContent);
        } catch (Exception e) {
            System.out.println("Error updating post.");
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- DELETE BY POST NAME ----------------
    public boolean deletePostByName(int userId, String postName) {
        try {
            Connection con = DBConnection.getConnection();

            int postId = dao.getPostIdByName(con, postName, userId);
            if (postId == -1) {
                System.out.println("Post not found!");
                return false;
            }

            return dao.deletePost(con, postId, userId);
        } catch (Exception e) {
            System.out.println("Error deleting post.");
            e.printStackTrace();
        }
        return false;
    }

    // ---------------- SEARCH BY HASHTAG ----------------
    public List<Post> searchByHashtag(String tag) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.searchByHashtag(con, tag);
        } catch (Exception e) {
            System.out.println("Error searching hashtag.");
            e.printStackTrace();
        }
        return new ArrayList<Post>();
    }

    // ---------------- TRENDING HASHTAGS ----------------
    public List<String> getTrendingHashtags() {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getTrendingHashtags(con);
        } catch (Exception e) {
            System.out.println("Error loading trending hashtags.");
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    // ---------------- POST ANALYTICS ----------------
    public Post getPostAnalytics(int postId) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getPostById(con, postId);
        } catch (Exception e) {
            System.out.println("Error loading post analytics.");
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- SHARE ----------------
    public void sharePost(int postId, String targetUser) {
        System.out.println("Sharing post ID " + postId + " with user " + targetUser);
    }
    public int getPostOwner(int postId) {
    	 try {
    	        Connection con = DBConnection.getConnection();
    	        return dao.getPostOwner(con, postId);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	    return -1;
    	}

}
