package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.revconnect.dao.ShareDAO;

public class ShareServiceTest {

    private ShareDAO shareDAO;
    private ShareService shareService;

    @Before
    public void setUp() {
        shareDAO = mock(ShareDAO.class);
        shareService = new ShareService(shareDAO);
    }

    @Test
    public void testSharePostSuccess() {

        when(shareDAO.sharePost(any(Connection.class), eq(10), eq(5)))
                .thenReturn(true);

        boolean result = shareService.sharePost(10, 5);

        assertTrue(result);

        verify(shareDAO, times(1))
                .sharePost(any(Connection.class), eq(10), eq(5));
    }

    @Test
    public void testGetShareCount() {

        when(shareDAO.getShareCount(any(Connection.class), eq(10)))
                .thenReturn(3);

        int count = shareService.getShareCount(10);

        assertEquals(3, count);

        verify(shareDAO, times(1))
                .getShareCount(any(Connection.class), eq(10));
    }
}
