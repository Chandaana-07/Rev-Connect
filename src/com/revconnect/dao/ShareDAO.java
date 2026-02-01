package com.revconnect.dao;
import java.sql.Connection;

public interface ShareDAO {

    boolean sharePost(Connection con,int postId, int userId);

    int getShareCount(Connection con,int postId);
}
