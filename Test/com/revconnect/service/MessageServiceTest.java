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

import com.revconnect.dao.MessageDAO;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.model.Message;

public class MessageServiceTest {

    @Mock
    private MessageDAO messageDAO;

    @Mock
    private ConnectionProvider connectionProvider;

    @Mock
    private Connection connection;

    private MessageService messageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(connectionProvider.getConnection())
            .thenReturn(connection);

        messageService =
            new MessageService(messageDAO, connectionProvider);
    }

    // ---------------- SEND MESSAGE ----------------
    @Test
    public void testSendMessageSuccess() {

        when(messageDAO.sendMessage(
                any(Connection.class),
                eq(1),
                eq(2),
                eq("Hello")))
            .thenReturn(true);

        boolean result =
            messageService.sendMessage(1, 2, "Hello");

        assertTrue(result);

        verify(messageDAO, times(1))
            .sendMessage(any(Connection.class), eq(1), eq(2), eq("Hello"));
    }

    // ---------------- GET INBOX ----------------
    @Test
    public void testGetInbox() {

        List<Message> list = new ArrayList<Message>();

        Message m = new Message();
        m.setSenderId(2);
        m.setReceiverId(1);
        m.setContent("Hi!");

        list.add(m);

        when(messageDAO.getInbox(any(Connection.class), eq(1)))
            .thenReturn(list);

        List<Message> result =
            messageService.getInbox(1);

        assertEquals(1, result.size());
        assertEquals("Hi!", result.get(0).getContent());

        verify(messageDAO, times(1))
            .getInbox(any(Connection.class), eq(1));
    }

    // ---------------- MARK READ ----------------
    @Test
    public void testMarkRead() {

        doNothing().when(messageDAO)
            .markRead(any(Connection.class), eq(1));

        messageService.markRead(1);

        verify(messageDAO, times(1))
            .markRead(any(Connection.class), eq(1));
    }

    // ---------------- GET CONVERSATION ----------------
    @Test
    public void testGetConversation() {

        List<Message> list = new ArrayList<Message>();

        Message m = new Message();
        m.setSenderId(1);
        m.setReceiverId(2);
        m.setContent("Hey");

        list.add(m);

        when(messageDAO.getConversation(any(Connection.class), eq(1), eq(2)))
            .thenReturn(list);

        List<Message> result =
            messageService.getConversation(1, 2);

        assertEquals(1, result.size());
        assertEquals("Hey", result.get(0).getContent());

        verify(messageDAO, times(1))
            .getConversation(any(Connection.class), eq(1), eq(2));
    }
}
