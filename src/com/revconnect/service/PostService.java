package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.model.Post;

public class PostService {

    private PostDAO postDAO;

    public PostService() {
        postDAO = new PostDAOImpl();
    }

    public boolean createPost(Post post) {
        return postDAO.createPost(post);
    }

    public List<Post> getPostsByUser(int userId) {
        return postDAO.getPostsByUser(userId);
    }

    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }
}
