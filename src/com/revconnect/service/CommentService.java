package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.Comment;
import com.revconnect.dao.CommentDAO;
import com.revconnect.dao.impl.CommentDAOImpl;


public class CommentService {

	 private CommentDAO dao = new CommentDAOImpl();
	 private NotificationService notificationService = new NotificationService();
	 private PostService postService = new PostService();
	 private UserService userService = new UserService();


   
	 public boolean addComment(Comment c) {

		    boolean success = dao.addComment(c);

		    if (success) {
		        int ownerId = postService.getPostOwner(c.getPostId());

		        // prevent notifying yourself
		        if (ownerId != c.getUserId()) {
		            String username = userService.getUsernameById(c.getUserId());

		            notificationService.notifyUser(
		                ownerId,
		                "@" + username + " commented on your post: \"" + c.getContent() + "\""
		            );
		        }
		    }

		    return success;
		}



    public List<Comment> getCommentsByPost(int postId) {
        return dao.getCommentsByPost(postId);
    }


    public void respondToComment(int commentId, String reply) {
        System.out.println("Reply to comment " + commentId + ": " + reply);
    }
    public boolean deleteComment(int commentId, int userId) {
        return dao.deleteComment(commentId, userId);
    }

}
