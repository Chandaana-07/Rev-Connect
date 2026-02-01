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

import com.revconnect.dao.NotificationDAO;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.model.Notification;

public class NotificationServiceTest {

    @Mock
    private NotificationDAO notificationDAO;

    @Mock
    private ConnectionProvider connectionProvider;

    @Mock
    private Connection connection;

    private NotificationService notificationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(connectionProvider.getConnection())
            .thenReturn(connection);

        notificationService =
            new NotificationService(notificationDAO, connectionProvider);
    }

    // ---------------- CREATE NOTIFICATION ----------------
    @Test
    public void testNotifyUser() {

        when(notificationDAO.createNotification(any(Notification.class)))
            .thenReturn(true);

        notificationService.notifyUser(1, "New comment");

        verify(notificationDAO, times(1))
            .createNotification(any(Notification.class));
    }

    // ---------------- GET MY NOTIFICATIONS ----------------
    @Test
    public void testGetMyNotifications() {

        List<Notification> list = new ArrayList<Notification>();

        Notification n = new Notification();
        n.setUserId(2);
        n.setMessage("New follower");

        list.add(n);

        when(notificationDAO.getMyNotifications(2))
            .thenReturn(list);

        List<Notification> result =
            notificationService.getMyNotifications(2);

        assertEquals(1, result.size());
        assertEquals("New follower",
            result.get(0).getMessage());

        verify(notificationDAO, times(1))
            .getMyNotifications(2);
    }

    // ---------------- UNREAD COUNT ----------------
    @Test
    public void testUnreadCount() {

        when(notificationDAO.getUnreadCount(3))
            .thenReturn(2);

        int count =
            notificationService.getUnreadCount(3);

        assertEquals(2, count);

        verify(notificationDAO, times(1))
            .getUnreadCount(3);
    }

    // ---------------- MARK AS READ ----------------
    @Test
    public void testMarkAsRead() {

        when(notificationDAO.markAsRead(10, 4))
            .thenReturn(true);

        boolean result =
            notificationService.markAsRead(10, 4);

        assertTrue(result);

        verify(notificationDAO, times(1))
            .markAsRead(10, 4);
    }
}
