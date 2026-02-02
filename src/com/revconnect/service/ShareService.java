package com.revconnect.service;

import java.sql.Connection;

import com.revconnect.dao.ShareDAO;
import com.revconnect.dao.impl.ShareDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
import org.apache.log4j.Logger;


public class ShareService {
	 private static final Logger logger =
	            Logger.getLogger(ShareService.class);
    private ShareDAO dao;
    private ConnectionProvider connectionProvider;
    

    // Normal constructor
    public ShareService() {
        this.dao = new ShareDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
        logger.info("ShareService initialized (Default Constructor)");

    }

   

    // SHARE POST
    public boolean sharePost(int postId, int userId) {

        logger.info("Share attempt | User ID: "
                + userId
                + " | Post ID: "
                + postId);

        try {
            Connection con = connectionProvider.getConnection();
            boolean success =
                    dao.sharePost(con, postId, userId);

            if (success) {
                logger.info("Post shared successfully | User ID: "
                        + userId
                        + " | Post ID: "
                        + postId);
            } else {
                logger.warn("Post share failed | User ID: "
                        + userId
                        + " | Post ID: "
                        + postId);
            }

            return success;

        } catch (Exception e) {
            logger.error("Error sharing post | User ID: "
                    + userId
                    + " | Post ID: "
                    + postId, e);
        }
        return false;
    }


    // SHARE COUNT
    public int getShareCount(int postId) {

        logger.info("Fetching share count | Post ID: " + postId);

        try {
            Connection con = connectionProvider.getConnection();
            int count =
                    dao.getShareCount(con, postId);

            logger.info("Share count fetched | Post ID: "
                    + postId
                    + " | Count: "
                    + count);

            return count;

        } catch (Exception e) {
            logger.error("Error fetching share count | Post ID: "
                    + postId, e);
        }
        return 0;
    }
 // For Mockito testing
    public ShareService(ShareDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
        logger.info("ShareService initialized (Test Constructor)");

    }

}
