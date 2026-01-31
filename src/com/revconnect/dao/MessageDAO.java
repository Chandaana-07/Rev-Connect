package com.revconnect.dao;

import java.sql.Connection;
import java.util.List;
import com.revconnect.model.Message;

public interface MessageDAO {
    boolean sendMessage(Connection con, int senderId, int receiverId, String content);
    List<Message> getInbox(Connection con, int userId);
    List<com.revconnect.model.Message> getConversation(
    	    java.sql.Connection con, int user1, int user2
    	);

    	void markRead(java.sql.Connection con, int userId);
}
