package com.revconnect.service;

import java.sql.Connection;
import java.util.List;

import com.revconnect.dao.MessageDAO;
import com.revconnect.dao.impl.MessageDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Message;
import com.revconnect.service.NotificationService;
import com.revconnect.service.UserService;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
import org.apache.log4j.Logger;


public class MessageService {
	
	Logger logger =
            Logger.getLogger(MessageService.class);
	private MessageDAO dao;
	private ConnectionProvider connectionProvider;
    private NotificationService notificationService = new NotificationService();
    private UserService userService = new UserService();


    // SEND MESSAGE
    public boolean send(Message m) {
    	logger.info("Send message started | From: "
                + m.getSenderId()
                + " To: "
                + m.getReceiverId());

        try {
            Connection con = connectionProvider.getConnection();
            return dao.sendMessage(
                con,
                m.getSenderId(),
                m.getReceiverId(),
                m.getContent()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // GET CONVERSATION
    public List<Message> getConversation(int user1, int user2) {
    	
    	logger.info("Fetching conversation between User "
                + user1 + " and User " + user2);
    	
        try {
            Connection con = connectionProvider.getConnection();
            return dao.getConversation(con, user1, user2);
        } catch (Exception e) {
        	logger.error("Error fetching conversation", e);
            e.printStackTrace();
        }
        return null;
    }

    // MARK READ
    public void markRead(int userId) {
    	logger.info("Marking messages as read for User ID: "
                + userId);
        try {
            Connection con = connectionProvider.getConnection();
            dao.markRead(con, userId);
            logger.info("Messages marked as read for User ID: "
                    + userId);
        } catch (Exception e) {
        	logger.error("Error marking messages as read", e);
            e.printStackTrace();
        }
    }

    // INBOX
    public List<Message> getInbox(int userId) {
    	logger.info("Fetching inbox for User ID: "
                + userId);

        try {
            Connection con = connectionProvider.getConnection();
            return dao.getInbox(con, userId);
        } catch (Exception e) {
        	logger.error("Error fetching inbox", e);
            e.printStackTrace();
        }
        return null;
    }
    public boolean sendMessage(int senderId, int receiverId, String content) {

        boolean success = send(new Message(senderId, receiverId, content));

        logger.info("sendMessage API called | From: "
                + senderId + " To: " + receiverId);
        
        if (success) {
            // get sender username (for nicer notification)
            String username = userService.getUsernameById(senderId);

            notificationService.notifyUser(
                receiverId,
                "@" + username + " sent you a message"
            );
            logger.info("Notification sent to User ID: "
                    + receiverId);
        }

        return success;
    }
 
    public MessageService() {
        this.dao = new MessageDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
        logger.info("MessageService initialized (Default Constructor)");
    }

   
    public MessageService(MessageDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
        logger.info("MessageService initialized (Test Constructor)");
    }


  
    
}
