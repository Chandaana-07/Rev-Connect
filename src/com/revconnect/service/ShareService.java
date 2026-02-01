package com.revconnect.service;

import java.sql.Connection;

import com.revconnect.dao.ShareDAO;
import com.revconnect.dao.impl.ShareDAOImpl;
import com.revconnect.db.DBConnection;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;


public class ShareService {

    private ShareDAO dao;
    private ConnectionProvider connectionProvider;
    

    // Normal constructor
    public ShareService() {
        this.dao = new ShareDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
    }

   

    // SHARE POST
    public boolean sharePost(int postId, int userId) {
        try {
            Connection con = connectionProvider.getConnection();
            return dao.sharePost(con, postId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // SHARE COUNT
    public int getShareCount(int postId) {
        try {
            Connection con = connectionProvider.getConnection();
            return dao.getShareCount(con, postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 // For Mockito testing
    public ShareService(ShareDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
    }

}
