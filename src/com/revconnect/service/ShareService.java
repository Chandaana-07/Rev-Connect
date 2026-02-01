package com.revconnect.service;

import java.sql.Connection;

import com.revconnect.dao.ShareDAO;
import com.revconnect.dao.impl.ShareDAOImpl;
import com.revconnect.db.DBConnection;

public class ShareService {

    private ShareDAO dao;

    // Normal constructor
    public ShareService() {
        this.dao = new ShareDAOImpl();
    }

    // Test constructor (for Mockito)
    public ShareService(ShareDAO dao) {
        this.dao = dao;
    }

    // SHARE POST
    public boolean sharePost(int postId, int userId) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.sharePost(con, postId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // SHARE COUNT
    public int getShareCount(int postId) {
        try {
            Connection con = DBConnection.getConnection();
            return dao.getShareCount(con, postId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
