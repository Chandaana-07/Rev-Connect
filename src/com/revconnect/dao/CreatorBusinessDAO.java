package com.revconnect.dao;

import com.revconnect.model.CreatorBusinessProfile;

public interface CreatorBusinessDAO {

    boolean registerProfile(CreatorBusinessProfile profile);
    boolean updateProfile(CreatorBusinessProfile profile);
    CreatorBusinessProfile getProfile(int userId);
}
