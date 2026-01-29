package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Message;

public interface MessageDAO {

    boolean sendMessage(Message message);

    List<Message> getInbox(int userId);

    List<Message> getConversation(int user1Id, int user2Id);
}
