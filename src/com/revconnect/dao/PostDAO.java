package com.revconnect.dao;

import java.sql.Connection;
import java.util.List;
import com.revconnect.model.Post;

public interface PostDAO {

    boolean createPost(Connection con, int userId, String postName, String content, String postType);

    List<Post> getPostsByUser(Connection con, int userId);

    List<Post> getAllPosts(Connection con);

    List<Post> getFeedPosts(Connection con, int userId);

    boolean updatePost(Connection con, int postId, int userId, String content);

    boolean deletePost(Connection con, int postId, int userId);

    int getPostIdByName(Connection con, String postName, int userId);

    List<Post> searchByHashtag(Connection con, String tag);

    List<String> getTrendingHashtags(Connection con);

    Post getPostById(Connection con, int postId);
}
