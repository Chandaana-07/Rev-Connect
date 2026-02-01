package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import com.revconnect.dao.ShareDAO;
import com.revconnect.db.ConnectionProvider;

public class ShareServiceTest {

    private ShareDAO shareDAO;
    private ConnectionProvider connectionProvider;
    private Connection connection;

    private ShareService shareService;

    @Before
    public void setUp() throws Exception {

        shareDAO = mock(ShareDAO.class);
        connectionProvider = mock(ConnectionProvider.class);
        connection = mock(Connection.class);

        when(connectionProvider.getConnection())
            .thenReturn(connection);

        shareService = new ShareService(shareDAO, connectionProvider);
    }

    @Test
    public void testSharePostSuccess() throws Exception {

        when(shareDAO.sharePost(connection, 10, 5))
            .thenReturn(true);

        boolean result =
            shareService.sharePost(10, 5);

        assertTrue(result);

        verify(shareDAO, times(1))
            .sharePost(connection, 10, 5);
    }

    @Test
    public void testGetShareCount() throws Exception {

        when(shareDAO.getShareCount(connection, 10))
            .thenReturn(3);

        int count =
            shareService.getShareCount(10);

        assertEquals(3, count);

        verify(shareDAO, times(1))
            .getShareCount(connection, 10);
    }
}
