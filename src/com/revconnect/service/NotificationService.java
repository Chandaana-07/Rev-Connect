package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.NotificationDAO;
import com.revconnect.dao.impl.NotificationDAOImpl;
import com.revconnect.model.Notification;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
import org.apache.log4j.Logger;
public class NotificationService {
	 private static final Logger logger =
	            Logger.getLogger(NotificationService.class);
    private NotificationDAO dao;
    private ConnectionProvider connectionProvider;

    // Create notification
    public void notifyUser(int userId, String message) {
    	 logger.info("Creating notification | User ID: "
                 + userId + " | Message: " + message);
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        boolean success = dao.createNotification(n); 
        if (success) {
            logger.info("Notification created successfully | User ID: "
                    + userId);
        } else {
            logger.warn("Failed to create notification | User ID: "
                    + userId);
        }}

    // Get all my notifications
    public List<Notification> getMyNotifications(int userId) {
    	 logger.info("Fetching notifications | User ID: "
                 + userId);

         List<Notification> list =
                 dao.getMyNotifications(userId);

         logger.info("Notifications fetched | User ID: "
                 + userId
                 + " | Count: "
                 + (list != null ? list.size() : 0));

         return list;
     }
    

    // Unread count
    public int getUnreadCount(int userId) {
    	 logger.info("Fetching unread count | User ID: "
                 + userId);

         int count =
                 dao.getUnreadCount(userId);

         logger.info("Unread count result | User ID: "
                 + userId
                 + " | Count: "
                 + count);

         return count;
    }

    // Mark single as read
    public boolean markAsRead(int notifId, int userId) {
    	logger.info("Marking notification as read | Notif ID: "
                + notifId
                + " | User ID: "
                + userId);

        boolean success =
                dao.markAsRead(notifId, userId);

        if (success) {
            logger.info("Notification marked as read | Notif ID: "
                    + notifId);
        } else {
            logger.warn("Failed to mark notification as read | Notif ID: "
                    + notifId);
        }

        return success;
    }
    

    // Mark all as read
    public void markAllRead(int userId) {
    	logger.info("Marking all notifications as read | User ID: "
                + userId);

        List<Notification> list =
                getMyNotifications(userId);

        if (list == null || list.isEmpty()) {
            logger.info("No notifications to mark as read | User ID: "
                    + userId);
            return;
        }

        for (Notification n : list) {
            markAsRead(n.getNotificationId(), userId);
        }

        logger.info("All notifications marked as read | User ID: "
                + userId);
    }

 // Normal Constructor
    public NotificationService() {
        this.dao = new NotificationDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
        logger.info("NotificationService initialized (Default Constructor)");

    }
   // TEST CONSTRUCTOR (Mockito uses this)
    public NotificationService(
            NotificationDAO dao,
            ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
        logger.info("NotificationService initialized (Test Constructor)");

    }
    public boolean createNotification(Notification n) {
    	  logger.info("Direct createNotification call | User ID: "
                  + n.getUserId());

          boolean success = dao.createNotification(n);

          if (success) {
              logger.info("Notification stored successfully | User ID: "
                      + n.getUserId());
          } else {
              logger.warn("Notification store failed | User ID: "
                      + n.getUserId());
          }

          return success;
      }
    }


