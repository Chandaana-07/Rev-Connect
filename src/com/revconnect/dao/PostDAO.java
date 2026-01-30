package com.revconnect.dao;

import java.util.List;

import com.revconnect.model.Post;

public interface PostDAO {

    boolean createPost(Post post);

    List<Post> getPostsByUser(int userId);

    List<Post> getAllPosts();
    
    boolean updatePost(int postId, int userId, String newContent);
    
    boolean deletePost(int postId, int userId);

    int getPostOwnerId(int postId);

    List<Post> getFeedPosts(int userId);


    List<Post> searchByHashtag(String tag);
    
    List<String> getTrendingHashtags();
    
    List<Post> getFilteredFeed(int userId, String Type);

    void incrementReach(int postId);
    
    Post getPostById(int postId);




}
