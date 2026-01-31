package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.model.Post;

public class PostDAOImpl implements PostDAO {

    // ---------------- CREATE ----------------
    @Override
    public boolean createPost(Connection con, int userId, String postName, String content, String postType) {

        PreparedStatement ps = null;

        try {
            String sql =
                "INSERT INTO POSTS (USER_ID, POST_NAME, CONTENT, POST_TYPE) " +
                "VALUES (?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, postName);
            ps.setString(3, content);
            ps.setString(4, postType);

            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println("You already have a post with this name. Please choose a different name.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, null);
        }

        return false;
    }

    // ---------------- VIEW MY POSTS ----------------
    @Override
    public List<Post> getPostsByUser(Connection con, int userId) {

        List<Post> list = new ArrayList<Post>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT p.POST_ID, p.POST_NAME, p.CONTENT, p.CREATED_AT, " +
                "p.POST_TYPE, p.REACH, u.USERNAME " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "WHERE p.USER_ID = ? " +
                "ORDER BY p.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();  

                p.setPostId(rs.getInt("POST_ID"));
                p.setPostName(rs.getString("POST_NAME"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setPostType(rs.getString("POST_TYPE"));
                p.setReach(rs.getInt("REACH"));
                p.setUsername(rs.getString("USERNAME"));
                p.setUserId(userId);

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return list;
    }


 // ---------------- GLOBAL FEED ----------------
    @Override
    public List<Post> getAllPosts(Connection con) {

        List<Post> list = new ArrayList<Post>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT p.POST_ID, p.USER_ID, p.POST_NAME, p.CONTENT, p.CREATED_AT, " +
                "p.POST_TYPE, p.REACH, u.USERNAME " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "ORDER BY p.CREATED_AT DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setUserId(rs.getInt("USER_ID"));
                p.setPostName(rs.getString("POST_NAME"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setPostType(rs.getString("POST_TYPE"));
                p.setReach(rs.getInt("REACH"));
                p.setUsername(rs.getString("USERNAME")); // Optional but useful

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return list;
    }


    // ---------------- CONNECTION FEED ----------------
    @Override
    public List<Post> getFeedPosts(Connection con, int userId) {
        // For now, same as global feed
        return getAllPosts(con);
    }

    // ---------------- UPDATE ----------------
    @Override
    public boolean updatePost(Connection con, int postId, int userId, String content) {

        PreparedStatement ps = null;

        try {
            String sql =
                "UPDATE POSTS SET CONTENT = ? " +
                "WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, content);
            ps.setInt(2, postId);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, null);
        }

        return false;
    }

    // ---------------- DELETE ----------------
    @Override
    public boolean deletePost(Connection con, int postId, int userId) {

        PreparedStatement ps = null;

        try {
            String sql =
                "DELETE FROM POSTS WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, null);
        }

        return false;
    }

    // ---------------- GET POST ID BY NAME ----------------
    @Override
    public int getPostIdByName(Connection con, String postName, int userId) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT POST_ID FROM POSTS " +
                "WHERE POST_NAME = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, postName);
            ps.setInt(2, userId);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("POST_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return -1;
    }

    // ---------------- SEARCH BY HASHTAG ----------------
    @Override
    public List<Post> searchByHashtag(Connection con, String tag) {

        List<Post> list = new ArrayList<Post>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT POST_ID, USER_ID, POST_NAME, CONTENT, CREATED_AT, POST_TYPE, REACH " +
                "FROM POSTS " +
                "WHERE CONTENT LIKE ? " +
                "ORDER BY CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tag + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setUserId(rs.getInt("USER_ID"));
                p.setPostName(rs.getString("POST_NAME"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setPostType(rs.getString("POST_TYPE"));
                p.setReach(rs.getInt("REACH"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return list;
    }

    // ---------------- TRENDING HASHTAGS ----------------
    @Override
    public List<String> getTrendingHashtags(Connection con) {

        List<String> list = new ArrayList<String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT SUBSTR(CONTENT, INSTR(CONTENT, '#')) AS TAG " +
                "FROM POSTS " +
                "WHERE CONTENT LIKE '#%'";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("TAG"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return list;
    }

    // ---------------- POST BY ID ----------------
    @Override
    public Post getPostById(Connection con, int postId) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                "SELECT POST_ID, USER_ID, POST_NAME, CONTENT, CREATED_AT, POST_TYPE, REACH " +
                "FROM POSTS WHERE POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            rs = ps.executeQuery();

            if (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setUserId(rs.getInt("USER_ID"));
                p.setPostName(rs.getString("POST_NAME"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setPostType(rs.getString("POST_TYPE"));
                p.setReach(rs.getInt("REACH"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, rs);
        }

        return null;
    }

    // ---------------- UTILITY ----------------
    private void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
