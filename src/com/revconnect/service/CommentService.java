package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.Comment;
import com.revconnect.dao.CommentDAO;
import com.revconnect.dao.impl.CommentDAOImpl;


public class CommentService {

	 private CommentDAO dao = new CommentDAOImpl();

    private static List<Comment> comments = new ArrayList<Comment>();
    private static int idCounter = 1;

    public void addComment(Comment c) {
        c.setCommentId(idCounter++);
        boolean comment = dao.addComment(c);
        
        if(comment){
        	System.out.println("Comment added");
        }
        else{
        	System.out.println("Comment not2 added");
        }
    }

    public List<Comment> getCommentsByPost(int postId) {
        List<Comment> list = new ArrayList<Comment>();
        for (Comment c : comments) {
            if (c.getPostId() == postId) list.add(c);
        }
        return list;
    }

    public void respondToComment(int commentId, String reply) {
        System.out.println("Reply to comment " + commentId + ": " + reply);
    }
    public boolean deleteComment(int commentId, int userId) {
        return dao.deleteComment(commentId, userId);
    }

}
