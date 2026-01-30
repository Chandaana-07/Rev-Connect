package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

        try {
            con = DBConnection.getConnection();

            String sql = "INSERT INTO posts " +
                    "(user_id, content, post_type, cta_text, tagged_product_id, scheduled_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getPostType());
            ps.setString(4, post.getCtaText());
            ps.setInt(5, post.getTaggedProductId());
            ps.setTimestamp(6, post.getScheduledTime());

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

    // ---------------- VIEW MY POSTS ----------------
    @Override
    public List<Post> getPostsByUser(int userId) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT P.POST_ID, P.USER_ID, U.USERNAME, P.CONTENT, P.CREATED_AT " +
                "FROM POSTS P " +
                "JOIN USERS U ON P.USER_ID = U.USER_ID " +
                "WHERE P.USER_ID = ? " +
                "ORDER BY P.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setUserId(rs.getInt("USER_ID"));
                p.setUsername(rs.getString("USERNAME"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(p);
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

        return list;
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
                "SELECT P.POST_ID, P.USER_ID, U.USERNAME, P.CONTENT, P.CREATED_AT " +
                "FROM POSTS P " +
                "JOIN USERS U ON P.USER_ID = U.USER_ID " +
                "ORDER BY P.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("POST_ID"));
                post.setUserId(rs.getInt("USER_ID"));
                post.setUsername(rs.getString("USERNAME"));
                post.setContent(rs.getString("CONTENT"));
                post.setCreatedAt(rs.getTimestamp("CREATED_AT"));
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

    // ---------------- CONNECTION FEED ----------------
    @Override
    public List<Post> getFeedPosts(int userId) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT P.POST_ID, P.CONTENT, P.CREATED_AT, U.USERNAME, " +
                "P.CTA_TEXT, P.REACH " +
                "FROM POSTS P " +
                "JOIN USERS U ON P.USER_ID = U.USER_ID " +
                "WHERE ( " +
                "   P.USER_ID = ? " +
                "   OR P.USER_ID IN ( " +
                "       SELECT CASE " +
                "           WHEN SENDER_ID = ? THEN RECEIVER_ID " +
                "           ELSE SENDER_ID " +
                "       END " +
                "       FROM CONNECTIONS " +
                "       WHERE (SENDER_ID = ? OR RECEIVER_ID = ?) " +
                "       AND STATUS = 'ACCEPTED' " +
                "   ) " +
                ") " +
                "AND (P.SCHEDULED_TIME IS NULL OR P.SCHEDULED_TIME <= SYSTIMESTAMP) " +
                "ORDER BY P.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setInt(4, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setUsername(rs.getString("USERNAME"));
                p.setCtaText(rs.getString("CTA_TEXT"));
                p.setReach(rs.getInt("REACH"));

                // increment reach each time feed is viewed
                incrementReach(p.getPostId());

                list.add(p);
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

        return list;
    }

    // ---------------- EDIT POST ----------------
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

    // ---------------- DELETE POST ----------------
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

    // ---------------- POST OWNER CHECK ----------------
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

    // ---------------- SEARCH POSTS BY HASHTAG ----------------
    @Override
    public List<Post> searchByHashtag(String tag) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT p.POST_ID, p.CONTENT, p.CREATED_AT, u.USERNAME " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "WHERE LOWER(p.CONTENT) LIKE ? " +
                "ORDER BY p.CREATED_AT DESC";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tag.toLowerCase() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("POST_ID"));
                post.setUsername(rs.getString("USERNAME"));
                post.setContent(rs.getString("CONTENT"));
                post.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                list.add(post);
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

        return list;
    }

    // ---------------- TRENDING HASHTAGS ----------------
    @Override
    public List<String> getTrendingHashtags() {

        List<String> list = new ArrayList<String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT * FROM ( " +
                "   SELECT hashtag, COUNT(*) AS cnt " +
                "   FROM POST_HASHTAGS " +
                "   GROUP BY hashtag " +
                "   ORDER BY cnt DESC " +
                ") WHERE ROWNUM <= 10";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                    rs.getString("hashtag") +
                    " (" + rs.getInt("cnt") + " posts)"
                );
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

        return list;
    }

    // ---------------- FILTER FEED ----------------
    @Override
    public List<Post> getFilteredFeed(int userId, String type) {

        List<Post> list = new ArrayList<Post>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql;

            if ("MY_POSTS".equals(type)) {
                sql =
                    "SELECT p.POST_ID, p.CONTENT, p.CREATED_AT, u.USERNAME " +
                    "FROM POSTS p JOIN USERS u ON p.USER_ID = u.USER_ID " +
                    "WHERE p.USER_ID = ? " +
                    "ORDER BY p.CREATED_AT DESC";

                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
            }
            else if ("CREATORS".equals(type)) {
                sql =
                    "SELECT p.POST_ID, p.CONTENT, p.CREATED_AT, u.USERNAME " +
                    "FROM POSTS p JOIN USERS u ON p.USER_ID = u.USER_ID " +
                    "WHERE u.ROLE IN ('BUSINESS','CREATOR') " +
                    "ORDER BY p.CREATED_AT DESC";

                ps = con.prepareStatement(sql);
            }
            else {
                sql =
                    "SELECT p.POST_ID, p.CONTENT, p.CREATED_AT, u.USERNAME " +
                    "FROM POSTS p JOIN USERS u ON p.USER_ID = u.USER_ID " +
                    "ORDER BY p.CREATED_AT DESC";

                ps = con.prepareStatement(sql);
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setContent(rs.getString("CONTENT"));
                p.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                p.setUsername(rs.getString("USERNAME"));
                list.add(p);
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

        return list;
    }

    // ---------------- POST ANALYTICS SUPPORT ----------------
    @Override
    public Post getPostById(int postId) {

        Post p = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT POST_ID, CONTENT, POST_TYPE, CTA_TEXT, TAGGED_PRODUCT_ID, " +
                "SCHEDULED_TIME, REACH " +
                "FROM POSTS WHERE POST_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            rs = ps.executeQuery();

            if (rs.next()) {
                p = new Post();
                p.setPostId(rs.getInt("POST_ID"));
                p.setContent(rs.getString("CONTENT"));
                p.setPostType(rs.getString("POST_TYPE"));
                p.setCtaText(rs.getString("CTA_TEXT"));
                p.setTaggedProductId(rs.getInt("TAGGED_PRODUCT_ID"));
                p.setScheduledTime(rs.getTimestamp("SCHEDULED_TIME"));
                p.setReach(rs.getInt("REACH"));
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

        return p;
    }

    // ---------------- REACH COUNTER ----------------
    @Override
    public void incrementReach(int postId) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE posts SET reach = reach + 1 WHERE post_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
