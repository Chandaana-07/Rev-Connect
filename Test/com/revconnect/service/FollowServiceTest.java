package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revconnect.dao.FollowDAO;
import com.revconnect.model.Follow;

public class FollowServiceTest {

    @Mock
    private FollowDAO followDAO;

    @Mock
    private NotificationService notificationService;

    private FollowService followService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create service manually
        followService = new FollowService();

        // Inject mocks using reflection
        try {
            java.lang.reflect.Field daoField =
                FollowService.class.getDeclaredField("dao");
            daoField.setAccessible(true);
            daoField.set(followService, followDAO);

            java.lang.reflect.Field notifField =
                FollowService.class.getDeclaredField("notificationService");
            notifField.setAccessible(true);
            notifField.set(followService, notificationService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------- FOLLOW USER ----------------
    @Test
    public void testFollowUserSuccess() {

        when(followDAO.followUser(1, 2))
            .thenReturn(true);

        boolean result =
            followService.followUser(1, 2);

        assertTrue(result);

        verify(followDAO, times(1))
            .followUser(1, 2);

        verify(notificationService, times(1))
            .notifyUser(2, "You have a new follower");
    }

    // ---------------- UNFOLLOW USER ----------------
    @Test
    public void testUnfollowUser() {

        when(followDAO.unfollowUser(1, 2))
            .thenReturn(true);

        boolean result =
            followService.unfollowUser(1, 2);

        assertTrue(result);

        verify(followDAO, times(1))
            .unfollowUser(1, 2);
    }

    // ---------------- GET FOLLOWERS ----------------
    @Test
    public void testGetFollowers() {

        List<Follow> list = new ArrayList<Follow>();
        list.add(new Follow());

        when(followDAO.getFollowers(5))
            .thenReturn(list);

        List<Follow> result =
            followService.getFollowers(5);

        assertEquals(1, result.size());

        verify(followDAO, times(1))
            .getFollowers(5);
    }

    // ---------------- GET FOLLOWING ----------------
    @Test
    public void testGetFollowing() {

        List<Follow> list = new ArrayList<Follow>();
        list.add(new Follow());

        when(followDAO.getFollowing(5))
            .thenReturn(list);

        List<Follow> result =
            followService.getFollowing(5);

        assertEquals(1, result.size());

        verify(followDAO, times(1))
            .getFollowing(5);
    }

    // ---------------- IS FOLLOWING ----------------
    @Test
    public void testIsFollowing() {

        when(followDAO.isFollowing(1, 2))
            .thenReturn(true);

        boolean result =
            followService.isFollowing(1, 2);

        assertTrue(result);

        verify(followDAO, times(1))
            .isFollowing(1, 2);
    }
}
