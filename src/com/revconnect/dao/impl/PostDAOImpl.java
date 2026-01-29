package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Post;

public class PostDAOImpl implements PostDAO {

    // ---------------- CREATE POST ----------------
    @Override
    public boolean createPost(Post post) {

        Connection con = null;
        PreparedStatement ps = null;
        boolean created = false;

        try {
            con = DBConnection.getConnection();

            String sql = "INSERT INTO POSTS (USER_ID, CONTENT) VALUES (?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());

            created = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return created;
    }

    // ---------------- VIEW MY POSTS ----------------
    @Override
    public List<Post> getPostsByUser(int userId) {

        List<Post> posts = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql = "SELECT POST_ID, USER_ID, CONTENT " +
                         "FROM POSTS WHERE USER_ID = ? " +
                         "ORDER BY POST_ID DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("POST_ID"));
                post.setUserId(rs.getInt("USER_ID"));
                post.setContent(rs.getString("CONTENT"));

                posts.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return posts;
    }

    // ---------------- GLOBAL FEED ----------------
    @Override
    public List<Post> getAllPosts() {

        List<Post> posts = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT p.POST_ID, p.USER_ID, p.CONTENT, p.CREATED_AT, u.USERNAME " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "ORDER BY p.POST_ID DESC";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("POST_ID"));
                post.setUserId(rs.getInt("USER_ID"));
                post.setContent(rs.getString("CONTENT"));
                post.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                post.setUsername(rs.getString("USERNAME"));
                posts.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return posts;
    }
    //----------------EDIT POSTS--------------------
    @Override
    public boolean updatePost(int postId, int userId, String newContent) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE POSTS SET CONTENT = ? " +
                "WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, newContent);
            ps.setInt(2, postId);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    @Override
    public boolean deletePost(int postId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM POSTS " +
                "WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    @Override
    public int getPostOwnerId(int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT USER_ID FROM POSTS WHERE POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("USER_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }



}
