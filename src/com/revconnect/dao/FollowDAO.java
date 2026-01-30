package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.Follow;

public interface FollowDAO {

    boolean followUser(int followerId, int followingId);

    boolean unfollowUser(int followerId, int followingId);

    boolean isFollowing(int followerId, int followingId);

    List<Follow> getFollowers(int userId);

    List<Follow> getFollowing(int userId);
}
