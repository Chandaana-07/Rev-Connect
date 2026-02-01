package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.revconnect.dao.LikeDAO;

public class LikeServiceTest {

    private LikeDAO likeDAO;
    private LikeService likeService;

    @Before
    public void setUp() {
        likeDAO = mock(LikeDAO.class);
        likeService = new LikeService(likeDAO);
    }

    @Test
    public void testLikePost() {

        when(likeDAO.likePost(1, 10))
            .thenReturn(true);

        likeService.likePost(1, 10);

        verify(likeDAO, times(1))
            .likePost(1, 10);
    }

    @Test
    public void testGetLikeCount() {

        when(likeDAO.getLikeCount(10))
            .thenReturn(5);

        int count =
            likeService.getLikeCount(10);

        assertEquals(5, count);

        verify(likeDAO, times(1))
            .getLikeCount(10);
    }

    @Test
    public void testUnlikePost() {

        when(likeDAO.unlikePost(1, 10))
            .thenReturn(true);

        boolean result =
            likeService.unlikePost(1, 10);

        assertTrue(result);

        verify(likeDAO, times(1))
            .unlikePost(1, 10);
    }
}
