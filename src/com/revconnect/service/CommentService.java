package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.CommentDAO;
import com.revconnect.dao.impl.CommentDAOImpl;
import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.model.Comment;

public class CommentService {

    private CommentDAO dao;
    private PostDAO postDAO;
    private NotificationService notificationService;

    public CommentService() {
        dao = new CommentDAOImpl();
        postDAO = new PostDAOImpl();
        notificationService = new NotificationService();
    }

    public boolean addComment(Comment comment) {

        boolean success = dao.addComment(comment);

        if (success) {
            int postOwnerId =
                postDAO.getPostOwnerId(comment.getPostId());

            if (postOwnerId != 0 &&
                postOwnerId != comment.getUserId()) {

                notificationService.notifyUser(
                    postOwnerId,
                    "Someone commented on your post"
                );
            }
        }

        return success;
    }

    public List<Comment> getCommentsByPost(int postId) {
        return dao.getCommentsByPost(postId);
    }

    public boolean deleteComment(int commentId, int userId) {
        return dao.deleteComment(commentId, userId);
    }
}
