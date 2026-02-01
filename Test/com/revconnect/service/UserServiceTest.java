package com.revconnect.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revconnect.dao.UserDAO;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.model.User;

public class UserServiceTest {

    private UserDAO userDAO;
    private ConnectionProvider connectionProvider;
    private Connection connection;

    private UserService userService;

    @Before
    public void setUp() throws Exception {

        userDAO = mock(UserDAO.class);
        connectionProvider = mock(ConnectionProvider.class);
        connection = mock(Connection.class);

        when(connectionProvider.getConnection())
            .thenReturn(connection);

        // IMPORTANT: This assumes you added this constructor:
        // public UserService(UserDAO dao, ConnectionProvider provider)
        userService = new UserService(userDAO, connectionProvider);
    }

    // ---------------- REGISTER ----------------
    @Test
    public void testRegisterUserSuccess() throws Exception {

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@gmail.com");
        user.setPassword("1234");

        when(userDAO.register(connection, user))
            .thenReturn(true);

        boolean result = userService.registerUser(user);

        assertTrue(result);

        verify(userDAO, times(1))
            .register(connection, user);
    }

    // ---------------- LOGIN ----------------
    @Test
    public void testLoginUserSuccess() throws Exception {

        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setUsername("testUser");

        when(userDAO.login(connection, "test@gmail.com", "1234"))
            .thenReturn(mockUser);

        User result =
            userService.loginUser("test@gmail.com", "1234");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());

        verify(userDAO, times(1))
            .login(connection, "test@gmail.com", "1234");
    }

    // ---------------- UPDATE PROFILE ----------------
    @Test
    public void testUpdateProfileSuccess() throws Exception {

        User user = new User();
        user.setUserId(1);
        user.setUsername("updated");

        when(userDAO.updateProfile(connection, user))
            .thenReturn(true);

        boolean result =
            userService.updateProfile(user);

        assertTrue(result);

        verify(userDAO, times(1))
            .updateProfile(connection, user);
    }

    // ---------------- SEARCH USERS ----------------
    @Test
    public void testSearchUsers() throws Exception {

        List<User> list = new ArrayList<User>();

        User u = new User();
        u.setUsername("john");
        list.add(u);

        when(userDAO.searchUsers(connection, "jo"))
            .thenReturn(list);

        List<User> result =
            userService.searchUsers("jo");

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getUsername());

        verify(userDAO, times(1))
            .searchUsers(connection, "jo");
    }

    // ---------------- GET USER ID ----------------
    @Test
    public void testGetUserIdByUsername() throws Exception {

        when(userDAO.getUserIdByUsername(connection, "testUser"))
            .thenReturn(10);

        int id =
            userService.getUserIdByUsername("testUser");

        assertEquals(10, id);

        verify(userDAO, times(1))
            .getUserIdByUsername(connection, "testUser");
    }
}
