package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revconnect.dao.ShareDAO;

public class ShareServiceTest {

    @Mock
    private ShareDAO shareDAO;

    private ShareService shareService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shareService = new ShareService(shareDAO); // inject mock DAO
    }

    // ---------------- TEST: sharePost SUCCESS ----------------
    @Test
    public void testSharePostSuccess() {
        int postId = 101;
        int userId = 5;

        when(shareDAO.sharePost(postId, userId))
            .thenReturn(true);

        boolean result =
            shareService.sharePost(postId, userId);

        assertTrue(result);

        verify(shareDAO, times(1))
            .sharePost(postId, userId);
    }

    // ---------------- TEST: sharePost FAILURE ----------------
    @Test
    public void testSharePostFailure() {
        int postId = 101;
        int userId = 5;

        when(shareDAO.sharePost(postId, userId))
            .thenReturn(false);

        boolean result =
            shareService.sharePost(postId, userId);

        assertFalse(result);

        verify(shareDAO, times(1))
            .sharePost(postId, userId);
    }

    // ---------------- TEST: getShareCount ----------------
    @Test
    public void testGetShareCount() {
        int postId = 101;

        when(shareDAO.getShareCount(postId))
            .thenReturn(7);

        int count =
            shareService.getShareCount(postId);

        assertEquals(7, count);

        verify(shareDAO, times(1))
            .getShareCount(postId);
    }
}
