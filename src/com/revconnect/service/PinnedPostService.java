package com.revconnect.service;

import java.util.ArrayList;
import java.util.List;
import com.revconnect.model.PinnedPost;

public class PinnedPostService {

    private static List<PinnedPost> pins = new ArrayList<PinnedPost>();

    public void pinPost(int userId, int postId) {
        pins.add(new PinnedPost(userId, postId));
        System.out.println("Post pinned to profile.");
    }

    public List<PinnedPost> getPinnedPosts(int userId) {
        List<PinnedPost> list = new ArrayList<PinnedPost>();
        for (PinnedPost p : pins) {
            if (p.getUserId() == userId) {
                list.add(p);
            }
        }
        return list;
    }
}
