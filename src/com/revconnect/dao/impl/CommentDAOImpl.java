package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.CommentDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Comment;

public class CommentDAOImpl implements CommentDAO {

    @Override
    public boolean addComment(Comment comment) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO COMMENTS (POST_ID, USER_ID, CONTENT) " +
                "VALUES (?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, comment.getPostId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getContent());

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
    public List<Comment> getCommentsByPost(int postId) {

        List<Comment> comments = new ArrayList<Comment>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT c.COMMENT_ID, c.USER_ID, c.CONTENT, c.CREATED_AT, u.USERNAME " +
                "FROM COMMENTS c " +
                "JOIN USERS u ON c.USER_ID = u.USER_ID " +
                "WHERE c.POST_ID = ? " +
                "ORDER BY c.CREATED_AT";

            ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("COMMENT_ID"));
                comment.setUserId(rs.getInt("USER_ID"));
                comment.setContent(rs.getString("CONTENT"));
                comment.setUsername(rs.getString("USERNAME"));
                comment.setCreatedAt(rs.getTimestamp("CREATED_AT"));

                comments.add(comment);
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

        return comments;
    }

    @Override
    public boolean deleteComment(int commentId, int userId) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "DELETE FROM COMMENTS " +
                "WHERE COMMENT_ID = ? AND USER_ID = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, commentId);
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
}
