package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDAO);
    }

    // ---------------- REGISTER ----------------
    @Test
    public void testRegisterUserSuccess() throws Exception {

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@gmail.com");
        user.setPassword("1234");

        when(userDAO.register(any(Connection.class), eq(user)))
            .thenReturn(true);

        boolean result = userService.registerUser(user);

        assertTrue(result);
        verify(userDAO, times(1))
            .register(any(Connection.class), eq(user));
    }

    // ---------------- LOGIN ----------------
    @Test
    public void testLoginUserSuccess() throws Exception {

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUsername("testUser");

        when(userDAO.login(any(Connection.class), eq("test@gmail.com"), eq("1234")))
            .thenReturn(mockUser);

        User result = userService.loginUser("test@gmail.com", "1234");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());

        verify(userDAO, times(1))
            .login(any(Connection.class), eq("test@gmail.com"), eq("1234"));
    }

    // ---------------- UPDATE PROFILE ----------------
    @Test
    public void testUpdateProfileSuccess() throws Exception {

        User user = new User();
        user.setUserId(1);
        user.setUsername("updatedUser");

        when(userDAO.updateProfile(any(Connection.class), eq(user)))
            .thenReturn(true);

        boolean result = userService.updateProfile(user);

        assertTrue(result);

        verify(userDAO, times(1))
            .updateProfile(any(Connection.class), eq(user));
    }

    // ---------------- GET USER BY USERNAME ----------------
    @Test
    public void testGetUserByUsername() throws Exception {

        User mockUser = new User();
        mockUser.setUsername("testUser");

        when(userDAO.getUserByUsername(any(Connection.class), eq("testUser")))
            .thenReturn(mockUser);

        User result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());

        verify(userDAO, times(1))
            .getUserByUsername(any(Connection.class), eq("testUser"));
    }

    // ---------------- EMAIL EXISTS ----------------
    @Test
    public void testEmailExistsTrue() throws Exception {

        User mockUser = new User();
        mockUser.setEmail("test@gmail.com");

        when(userDAO.getUserByEmail(any(Connection.class), eq("test@gmail.com")))
            .thenReturn(mockUser);

        boolean exists = userService.emailExists("test@gmail.com");

        assertTrue(exists);

        verify(userDAO, times(1))
            .getUserByEmail(any(Connection.class), eq("test@gmail.com"));
    }

    // ---------------- USERNAME EXISTS ----------------
    @Test
    public void testUsernameExistsTrue() throws Exception {

        User mockUser = new User();
        mockUser.setUsername("testUser");

        when(userDAO.getUserByUsernameExact(any(Connection.class), eq("testUser")))
            .thenReturn(mockUser);

        boolean exists = userService.usernameExists("testUser");

        assertTrue(exists);

        verify(userDAO, times(1))
            .getUserByUsernameExact(any(Connection.class), eq("testUser"));
    }

    // ---------------- RESET PASSWORD ----------------
    @Test
    public void testResetPasswordSuccess() throws Exception {

        when(userDAO.resetPassword(any(Connection.class),
                eq("test@gmail.com"), eq("newpass")))
            .thenReturn(true);

        boolean result =
            userService.resetPassword("test@gmail.com", "newpass");

        assertTrue(result);

        verify(userDAO, times(1))
            .resetPassword(any(Connection.class),
                eq("test@gmail.com"), eq("newpass"));
    }

    // ---------------- SEARCH USERS ----------------
    @Test
    public void testSearchUsers() throws Exception {

        List<User> mockList = new ArrayList<User>();

        User u1 = new User();
        u1.setUsername("john");
        mockList.add(u1);

        when(userDAO.searchUsers(any(Connection.class), eq("jo")))
            .thenReturn(mockList);

        List<User> result =
            userService.searchUsers("jo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getUsername());

        verify(userDAO, times(1))
            .searchUsers(any(Connection.class), eq("jo"));
    }

    // ---------------- GET USER ID ----------------
    @Test
    public void testGetUserIdByUsername() throws Exception {

        when(userDAO.getUserIdByUsername(any(Connection.class), eq("testUser")))
            .thenReturn(10);

        int id = userService.getUserIdByUsername("testUser");

        assertEquals(10, id);

        verify(userDAO, times(1))
            .getUserIdByUsername(any(Connection.class), eq("testUser"));
    }
}
