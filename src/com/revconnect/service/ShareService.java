package com.revconnect.service;

import com.revconnect.dao.ShareDAO;
import com.revconnect.dao.impl.ShareDAOImpl;

public class ShareService {

    private ShareDAO dao;

    public ShareService() {
        dao = new ShareDAOImpl();
    }

    public boolean sharePost(int postId, int userId) {
        return dao.sharePost(postId, userId);
    }

    public int getShareCount(int postId) {
        return dao.getShareCount(postId);
    }
    public ShareService(ShareDAO dao) {
        this.dao = dao;
    }
}
