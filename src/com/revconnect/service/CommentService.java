package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.Comment;
import com.revconnect.dao.CommentDAO;
import com.revconnect.dao.impl.CommentDAOImpl;
import org.apache.log4j.Logger;
public class CommentService {
	 private static final Logger logger =
	            Logger.getLogger(CommentService.class);

	 private NotificationService notificationService = new NotificationService();
	 private PostService postService = new PostService();
	 private UserService userService = new UserService();
	 private CommentDAO dao;
	


   
	 public boolean addComment(Comment c) {
	     logger.info("Add comment attempt | User ID: "
	                + c.getUserId()
	                + " | Post ID: "
	                + c.getPostId());
		
		    boolean success = dao.addComment(c);

		    if (success) {
		    	logger.info("Comment added successfully | Comment: "
	                    + c.getContent());
		        int ownerId = postService.getPostOwner(c.getPostId());

		        
		        if (ownerId != c.getUserId()) {
		            String username = userService.getUsernameById(c.getUserId());

		            notificationService.notifyUser(
		                ownerId,
		                "@" + username + " commented on your post: \"" + c.getContent() + "\""
		            );
		            logger.info("Comment notification sent | From User ID: "
	                        + c.getUserId()
	                        + " | To User ID: "
	                        + ownerId);
		        }else {
	                logger.info("Notification skipped (self-comment)");
	            }
		    }else {
	            logger.warn("Failed to add comment | User ID: "
	                    + c.getUserId()
	                    + " | Post ID: "
	                    + c.getPostId());
	        }


		    return success;
		}



    public List<Comment> getCommentsByPost(int postId) {
    	 logger.info("Fetching comments | Post ID: " + postId);

         List<Comment> list =
                 dao.getCommentsByPost(postId);

         logger.info("Comments fetched | Post ID: "
                 + postId
                 + " | Count: "
                 + (list != null ? list.size() : 0));

         return list;
         }


    public void respondToComment(int commentId, String reply) {
    	 logger.info("Responding to comment | Comment ID: "
                 + commentId
                 + " | Reply: "
                 + reply);

         System.out.println(
                 "Reply to comment "
                 + commentId
                 + ": "
                 + reply
         );
         }
    public boolean deleteComment(int commentId, int userId) {
    	 logger.info("Delete comment attempt | Comment ID: "
                 + commentId
                 + " | User ID: "
                 + userId);

         boolean success =
                 dao.deleteComment(commentId, userId);

         if (success) {
             logger.info("Comment deleted successfully | Comment ID: "
                     + commentId);
         } else {
             logger.warn("Failed to delete comment | Comment ID: "
                     + commentId);
         }

         return success;
         }
 
    public CommentService() {
        this.dao = new CommentDAOImpl();
        logger.info("CommentService initialized");
    }

 
  

}
