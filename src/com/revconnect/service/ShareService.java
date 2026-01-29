package com.revconnect.service;

import com.revconnect.dao.ShareDAO;
import com.revconnect.dao.impl.ShareDAOImpl;

public class ShareService {

    private ShareDAO shareDAO;

    public ShareService() {
        shareDAO = new ShareDAOImpl();
    }

    public boolean sharePost(int postId, int userId) {
        return shareDAO.sharePost(postId, userId);
    }

    public int getShareCount(int postId) {
        return shareDAO.getShareCount(postId);
    }
}
