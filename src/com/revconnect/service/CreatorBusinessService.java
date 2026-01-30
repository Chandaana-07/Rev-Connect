package com.revconnect.service;

import com.revconnect.dao.CreatorBusinessDAO;
import com.revconnect.dao.impl.CreatorBusinessDAOImpl;
import com.revconnect.model.CreatorBusinessProfile;

public class CreatorBusinessService {

    private CreatorBusinessDAO dao = new CreatorBusinessDAOImpl();

    public boolean register(CreatorBusinessProfile p) {
        return dao.registerProfile(p);
    }

    public boolean update(CreatorBusinessProfile p) {
        return dao.updateProfile(p);
    }

    public CreatorBusinessProfile getProfile(int userId) {
        return dao.getProfile(userId);
    }
}
