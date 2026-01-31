package com.revconnect.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Post;

public class PostService {

    private PostDAO dao;

    public PostService() {
        dao = new PostDAOImpl();
    }

    // ---------------- CREATE ----------------
    public boolean createPost(int userId, String postName, String content, String postType) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.createPost(con, userId, postName, content, postType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- VIEW MY POSTS ----------------
    public List<Post> getPostsByUser(int userId) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getPostsByUser(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return new ArrayList<Post>();
    }

    // ---------------- GLOBAL FEED ----------------
    public List<Post> getAllPosts() {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getAllPosts(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return new ArrayList<Post>();
    }

    // ---------------- CONNECTION FEED ----------------
    public List<Post> getFeedPosts(int userId) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getFeedPosts(con, userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return new ArrayList<Post>();
    }

    // ---------------- EDIT BY POST NAME ----------------
    public boolean updatePostByName(int userId, String postName, String newContent) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();

            int postId = dao.getPostIdByName(con, postName, userId);
            if (postId == -1) {
                System.out.println("Post not found!");
                return false;
            }

            return dao.updatePost(con, postId, userId, newContent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- DELETE BY POST NAME ----------------
    public boolean deletePostByName(int userId, String postName) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();

            int postId = dao.getPostIdByName(con, postName, userId);
            if (postId == -1) {
                System.out.println("Post not found!");
                return false;
            }

            return dao.deletePost(con, postId, userId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return false;
    }

    // ---------------- SEARCH BY HASHTAG ----------------
    public List<Post> searchByHashtag(String tag) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.searchByHashtag(con, tag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return new ArrayList<Post>();
    }

    // ---------------- TRENDING HASHTAGS ----------------
    public List<String> getTrendingHashtags() {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getTrendingHashtags(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return new ArrayList<String>();
    }

    // ---------------- POST ANALYTICS ----------------
    public Post getPostAnalytics(int postId) {

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            return dao.getPostById(con, postId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    // ---------------- SHARE ----------------
    public void sharePost(int postId, String targetUser) {
        System.out.println("Sharing post ID " + postId + " with user " + targetUser);
    }

    // ---------------- UTILITY ----------------
    private void close(Connection con) {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
