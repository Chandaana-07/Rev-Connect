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

    // ---------------- CREATE ----------------
    @Override
    public boolean createPost(Connection ignored, int userId, String postName, String content, String postType) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

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
            close(null, ps, con);
        }

        return false;
    }

    // ---------------- VIEW MY POSTS ----------------
    @Override
    public List<Post> getPostsByUser(Connection ignored, int userId) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

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
            close(rs, ps, con);
        }

        return list;
    }

    // ---------------- GLOBAL FEED ----------------
    @Override
    public List<Post> getAllPosts(Connection ignored) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

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
                p.setUsername(rs.getString("USERNAME"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }

        return list;
    }

    // ---------------- CONNECTION FEED ----------------
    @Override
    public List<Post> getFeedPosts(Connection ignored, int userId) {
        return getAllPosts(null);
    }

    // ---------------- UPDATE ----------------
    @Override
    public boolean updatePost(Connection ignored, int postId, int userId, String content) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

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
            close(null, ps, con);
        }

        return false;
    }

    // ---------------- DELETE ----------------
    @Override
    public boolean deletePost(Connection ignored, int postId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM POSTS WHERE POST_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }

        return false;
    }

    // ---------------- GET POST ID BY NAME ----------------
    @Override
    public int getPostIdByName(Connection ignored, String postName, int userId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

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
            close(rs, ps, con);
        }

        return -1;
    }

    // ---------------- SEARCH BY HASHTAG ----------------
    @Override
    public List<Post> searchByHashtag(Connection ignored, String tag) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT p.POST_ID, p.USER_ID, p.POST_NAME, p.CONTENT, p.CREATED_AT, " +
                "p.POST_TYPE, p.REACH, u.USERNAME " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "WHERE LOWER(p.POST_NAME) LIKE ? OR LOWER(p.CONTENT) LIKE ? " +
                "ORDER BY p.CREATED_AT DESC";

            ps = con.prepareStatement(sql);

            String search = "%" + tag.toLowerCase() + "%";
            ps.setString(1, search);
            ps.setString(2, search);

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
                p.setUsername(rs.getString("USERNAME"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }

        return list;
    }

    // ---------------- TRENDING HASHTAGS ----------------
    @Override
    public List<String> getTrendingHashtags(Connection ignored) {

        List<String> list = new ArrayList<String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

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
            close(rs, ps, con);
        }

        return list;
    }

    // ---------------- POST BY ID ----------------
    @Override
    public Post getPostById(Connection ignored, int postId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

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
            close(rs, ps, con);
        }

        return null;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
    @Override
    public int getPostOwner(Connection con, int postId) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(
                "SELECT USER_ID FROM POSTS WHERE POST_ID = ?"
            );
            ps.setInt(1, postId);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("USER_ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return -1;
    }



}
