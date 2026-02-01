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

    // ---------------- ADD COMMENT ----------------
	@Override
	public boolean addComment(Comment c) {

	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = DBConnection.getConnection();

	        String sql =
	            "INSERT INTO comments " +
	            "(comment_id, post_id, user_id, content) " +
	            "VALUES (comments_seq.NEXTVAL, ?, ?, ?)";

	        ps = con.prepareStatement(sql);
	        ps.setInt(1, c.getPostId());
	        ps.setInt(2, c.getUserId());
	        ps.setString(3, c.getContent());

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        System.out.println("ERROR inserting comment:");
	        e.printStackTrace();
	    } finally {
	        closeResources(null, ps, con);
	    }

	    return false;
	}


    // ---------------- GET COMMENTS BY POST ----------------
	@Override
	public List<Comment> getCommentsByPost(int postId) {

	    List<Comment> list = new ArrayList<Comment>();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = DBConnection.getConnection();

	        String sql =
	            "SELECT c.comment_id, c.post_id, c.user_id, c.content, c.created_at, " +
	            "u.username " +
	            "FROM comments c " +
	            "LEFT JOIN users u ON c.user_id = u.user_id " +
	            "WHERE c.post_id = ? " +
	            "ORDER BY c.created_at ASC";

	        ps = con.prepareStatement(sql);
	        ps.setInt(1, postId);

	        rs = ps.executeQuery();
	        while (rs.next()) {

	            Comment c = new Comment();
	            c.setCommentId(rs.getInt("COMMENT_ID"));
	            c.setPostId(rs.getInt("POST_ID"));
	            c.setUserId(rs.getInt("USER_ID"));
	            c.setContent(rs.getString("CONTENT"));
	            c.setCreatedAt(rs.getTimestamp("CREATED_AT"));
	            c.setUsername(rs.getString("USERNAME"));

	            list.add(c);
	        }

	    } catch (Exception e) {
	        System.out.println("ERROR fetching comments:");
	        e.printStackTrace();
	    } finally {
	        closeResources(rs, ps, con);
	    }

	    return list;
	}


    // ---------------- DELETE COMMENT ----------------
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
            close(null, ps, con);
        }

        return false;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
 // ================= CLOSE DB RESOURCES =================
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
        }

        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
        }

        try {
            if (con != null) con.close();
        } catch (Exception e) {
        }
    }

}
