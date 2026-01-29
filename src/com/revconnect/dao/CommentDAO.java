package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Comment;

public interface CommentDAO {

    boolean addComment(Comment comment);

    List<Comment> getCommentsByPost(int postId);

    boolean deleteComment(int commentId, int userId);
}
