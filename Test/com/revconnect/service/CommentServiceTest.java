package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revconnect.dao.CommentDAO;
import com.revconnect.model.Comment;

public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    private CommentService commentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create service
        commentService = new CommentService();

        // Inject mock DAO using reflection (no functionality change)
        try {
            java.lang.reflect.Field field =
                CommentService.class.getDeclaredField("dao");

            field.setAccessible(true);
            field.set(commentService, commentDAO);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------- ADD COMMENT ----------------
    @Test
    public void testAddComment() {

        Comment c = new Comment();
        c.setPostId(5);
        c.setUserId(1);
        c.setContent("Nice post");

        when(commentDAO.addComment(c))
            .thenReturn(true);

        boolean result =
            commentService.addComment(c);

        assertTrue(result);

        verify(commentDAO, times(1))
            .addComment(c);
    }

    // ---------------- GET COMMENTS ----------------
    @Test
    public void testGetCommentsByPost() {

        List<Comment> list = new ArrayList<Comment>();

        Comment c = new Comment();
        c.setPostId(5);
        c.setContent("Hello");

        list.add(c);

        when(commentDAO.getCommentsByPost(5))
            .thenReturn(list);

        List<Comment> result =
            commentService.getCommentsByPost(5);

        assertEquals(1, result.size());
        assertEquals("Hello", result.get(0).getContent());

        verify(commentDAO, times(1))
            .getCommentsByPost(5);
    }

    // ---------------- DELETE COMMENT ----------------
    @Test
    public void testDeleteComment() {

        when(commentDAO.deleteComment(10, 1))
            .thenReturn(true);

        boolean result =
            commentService.deleteComment(10, 1);

        assertTrue(result);

        verify(commentDAO, times(1))
            .deleteComment(10, 1);
    }
}
