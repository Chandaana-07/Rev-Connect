package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revconnect.dao.PostDAO;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.model.Post;

public class PostServiceTest {

    @Mock
    private PostDAO postDAO;

    @Mock
    private ConnectionProvider connectionProvider;

    @Mock
    private Connection connection;

    private PostService postService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Fake DB connection
        when(connectionProvider.getConnection())
            .thenReturn(connection);

        // Inject mocks into service
        postService = new PostService(postDAO, connectionProvider);
    }

    // ---------------- CREATE POST ----------------
    @Test
    public void testCreatePostSuccess() throws Exception {
        when(postDAO.createPost(
                connection, 1, "Test Post", "Hello", "NORMAL"))
            .thenReturn(true);

        boolean result =
            postService.createPost(
                1, "Test Post", "Hello", "NORMAL");

        assertTrue(result);

        verify(postDAO, times(1))
            .createPost(connection, 1, "Test Post", "Hello", "NORMAL");
    }

    // ---------------- GET POSTS BY USER ----------------
    @Test
    public void testGetPostsByUser() throws Exception {
        List<Post> mockList = new ArrayList<Post>();
        mockList.add(new Post());

        when(postDAO.getPostsByUser(connection, 1))
            .thenReturn(mockList);

        List<Post> result =
            postService.getPostsByUser(1);

        assertEquals(1, result.size());

        verify(postDAO, times(1))
            .getPostsByUser(connection, 1);
    }

    // ---------------- UPDATE POST SUCCESS ----------------
    @Test
    public void testUpdatePostByNameSuccess() throws Exception {
        when(postDAO.getPostIdByName(connection, "MyPost", 1))
            .thenReturn(10);

        when(postDAO.updatePost(connection, 10, 1, "Updated"))
            .thenReturn(true);

        boolean result =
            postService.updatePostByName(
                1, "MyPost", "Updated");

        assertTrue(result);

        verify(postDAO).getPostIdByName(connection, "MyPost", 1);
        verify(postDAO).updatePost(connection, 10, 1, "Updated");
    }

    // ---------------- UPDATE POST NOT FOUND ----------------
    @Test
    public void testUpdatePostByNameNotFound() throws Exception {
        when(postDAO.getPostIdByName(connection, "Missing", 1))
            .thenReturn(-1);

        boolean result =
            postService.updatePostByName(
                1, "Missing", "Updated");

        assertFalse(result);

        verify(postDAO).getPostIdByName(connection, "Missing", 1);
        verify(postDAO, never())
            .updatePost(any(Connection.class),
                        anyInt(),
                        anyInt(),
                        anyString());
    }

    // ---------------- DELETE POST ----------------
    @Test
    public void testDeletePostByNameSuccess() throws Exception {
        when(postDAO.getPostIdByName(connection, "MyPost", 1))
            .thenReturn(10);

        when(postDAO.deletePost(connection, 10, 1))
            .thenReturn(true);

        boolean result =
            postService.deletePostByName(1, "MyPost");

        assertTrue(result);

        verify(postDAO).deletePost(connection, 10, 1);
    }

    // ---------------- SEARCH BY HASHTAG ----------------
    @Test
    public void testSearchByHashtag() throws Exception {
        List<Post> mockList = new ArrayList<Post>();
        mockList.add(new Post());

        when(postDAO.searchByHashtag(connection, "#java"))
            .thenReturn(mockList);

        List<Post> result =
            postService.searchByHashtag("#java");

        assertEquals(1, result.size());

        verify(postDAO).searchByHashtag(connection, "#java");
    }

    // ---------------- TRENDING TAGS ----------------
    @Test
    public void testGetTrendingHashtags() throws Exception {
        List<String> tags = new ArrayList<String>();
        tags.add("#java");

        when(postDAO.getTrendingHashtags(connection))
            .thenReturn(tags);

        List<String> result =
            postService.getTrendingHashtags();

        assertEquals(1, result.size());

        verify(postDAO).getTrendingHashtags(connection);
    }

    // ---------------- POST ANALYTICS ----------------
    @Test
    public void testGetPostAnalytics() throws Exception {
        Post post = new Post();

        when(postDAO.getPostById(connection, 5))
            .thenReturn(post);

        Post result =
            postService.getPostAnalytics(5);

        assertNotNull(result);

        verify(postDAO).getPostById(connection, 5);
    }
}
