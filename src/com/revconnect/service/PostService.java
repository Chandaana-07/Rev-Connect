package com.revconnect.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.PostDAO;
import com.revconnect.dao.impl.PostDAOImpl;
import com.revconnect.db.ConnectionProvider;
import com.revconnect.db.DefaultConnectionProvider;
import com.revconnect.db.DBConnection;
import com.revconnect.model.Post;
import org.apache.log4j.Logger;

public class PostService {
	private static final Logger logger =
            Logger.getLogger(PostService.class);
    private PostDAO dao;
    private ConnectionProvider connectionProvider;

    // ================= DEFAULT CONSTRUCTOR =================
    public PostService() {
        this.dao = new PostDAOImpl();
        this.connectionProvider = new DefaultConnectionProvider();
        logger.info("PostService initialized (Default Constructor)");

    }

    // ================= TEST CONSTRUCTOR =================
    public PostService(PostDAO dao, ConnectionProvider provider) {
        this.dao = dao;
        this.connectionProvider = provider;
        logger.info("PostService initialized (Test Constructor)");

    }

    // ---------------- CREATE ----------------
    public boolean createPost(int userId, String postName, String content, String postType) {
    	  logger.info("Create post attempt | User ID: "
                  + userId
                  + " | Post Name: "
                  + postName
                  + " | Type: "
                  + postType);
    	  try {
              Connection con = DBConnection.getConnection();
              boolean success =
                      dao.createPost(con, userId, postName, content, postType);

              if (success) {
                  logger.info("Post created successfully | User ID: "
                          + userId
                          + " | Post Name: "
                          + postName);
              } else {
                  logger.warn("Post creation failed | User ID: "
                          + userId
                          + " | Post Name: "
                          + postName);
              }

              return success;

          } catch (Exception e) {
              logger.error("Error creating post | User ID: "
                      + userId
                      + " | Post Name: "
                      + postName, e);
          }
        return false;
    }

    // ---------------- VIEW MY POSTS ----------------
    public List<Post> getPostsByUser(int userId) {

        logger.info("Fetching posts by user | User ID: " + userId);

        try {
            Connection con = DBConnection.getConnection();
            List<Post> list =
                    dao.getPostsByUser(con, userId);

            logger.info("Posts fetched | User ID: "
                    + userId
                    + " | Count: "
                    + list.size());

            return list;

        } catch (Exception e) {
            logger.error("Error fetching posts | User ID: " + userId, e);
        }
        return new ArrayList<Post>();
    }

    // ---------------- GLOBAL FEED ----------------
    public List<Post> getAllPosts() {

        logger.info("Fetching global feed");

        try {
            Connection con = DBConnection.getConnection();
            List<Post> list =
                    dao.getAllPosts(con);

            logger.info("Global feed loaded | Count: " + list.size());

            return list;

        } catch (Exception e) {
            logger.error("Error loading global feed", e);
        }
        return new ArrayList<Post>();
    }
    // ---------------- CONNECTION FEED ----------------
    public List<Post> getFeedPosts(int userId) {

        logger.info("Fetching feed | User ID: " + userId);

        try {
            Connection con = DBConnection.getConnection();
            List<Post> list =
                    dao.getFeedPosts(con, userId);

            logger.info("Feed loaded | User ID: "
                    + userId
                    + " | Count: "
                    + list.size());

            return list;

        } catch (Exception e) {
            logger.error("Error loading feed | User ID: " + userId, e);
        }
        return new ArrayList<Post>();
    }

    // ---------------- EDIT BY POST NAME ----------------
    public boolean updatePostByName(int userId,
            String postName,
            String newContent) {

    		logger.info("Update post attempt | User ID: "
    				+ userId
    				+ " | Post Name: "
			+ postName);
			
			try {
			Connection con = DBConnection.getConnection();
			
			int postId =
			dao.getPostIdByName(con, postName, userId);
			
			if (postId == -1) {
			logger.warn("Post not found | User ID: "
			+ userId
			+ " | Post Name: "
			+ postName);
			return false;
			}
			
			boolean success =
			dao.updatePost(con, postId, userId, newContent);
			
			if (success) {
			logger.info("Post updated successfully | Post ID: " + postId);
			} else {
			logger.warn("Post update failed | Post ID: " + postId);
			}
			
			return success;
			
			} catch (Exception e) {
			logger.error("Error updating post | User ID: "
			+ userId
			+ " | Post Name: "
			+ postName, e);
			}
			return false;
			}
			

    // ---------------- DELETE BY POST NAME ----------------
    public boolean deletePostByName(int userId,
            String postName) {

			logger.info("Delete post attempt | User ID: "
			+ userId
			+ " | Post Name: "
			+ postName);
			
			try {
			Connection con = DBConnection.getConnection();
			
			int postId =
			dao.getPostIdByName(con, postName, userId);
			
			if (postId == -1) {
			logger.warn("Post not found | User ID: "
			+ userId
			+ " | Post Name: "
			+ postName);
			return false;
			}
			
			boolean success =
			dao.deletePost(con, postId, userId);
			
			if (success) {
			logger.info("Post deleted successfully | Post ID: " + postId);
			} else {
			logger.warn("Post delete failed | Post ID: " + postId);
			}
			
			return success;
			
			} catch (Exception e) {
			logger.error("Error deleting post | User ID: "
			+ userId
			+ " | Post Name: "
			+ postName, e);
			}
			return false;
			}
    // ---------------- SEARCH BY HASHTAG ----------------
    public List<Post> searchByHashtag(String tag) {

        logger.info("Search by hashtag | Tag: " + tag);

        try {
            Connection con = DBConnection.getConnection();
            List<Post> list =
                    dao.searchByHashtag(con, tag);

            logger.info("Hashtag search result | Tag: "
                    + tag
                    + " | Count: "
                    + list.size());

            return list;

        } catch (Exception e) {
            logger.error("Error searching hashtag | Tag: " + tag, e);
        }
        return new ArrayList<Post>();
    }

    // ---------------- TRENDING HASHTAGS ----------------
    public List<String> getTrendingHashtags() {

        logger.info("Fetching trending hashtags");

        try {
            Connection con = DBConnection.getConnection();
            List<String> list =
                    dao.getTrendingHashtags(con);

            logger.info("Trending hashtags loaded | Count: "
                    + list.size());

            return list;

        } catch (Exception e) {
            logger.error("Error loading trending hashtags", e);
        }
        return new ArrayList<String>();
    }

    // ---------------- POST ANALYTICS ----------------
    public Post getPostAnalytics(int postId) {

        logger.info("Fetching post analytics | Post ID: " + postId);

        try {
            Connection con = DBConnection.getConnection();
            Post post =
                    dao.getPostById(con, postId);

            if (post != null) {
                logger.info("Post analytics loaded | Post ID: " + postId);
            } else {
                logger.warn("Post not found | Post ID: " + postId);
            }

            return post;

        } catch (Exception e) {
            logger.error("Error loading post analytics | Post ID: "
                    + postId, e);
        }
        return null;
    }

    // ---------------- SHARE ----------------
    public void sharePost(int postId, String targetUser) {

        logger.info("Share post | Post ID: "
                + postId
                + " | Target User: "
                + targetUser);

        System.out.println("Sharing post ID "
                + postId
                + " with user "
                + targetUser);
    }
    public int getPostOwner(int postId) {

        logger.info("Fetching post owner | Post ID: " + postId);

        try {
            Connection con = DBConnection.getConnection();
            int ownerId =
                    dao.getPostOwner(con, postId);

            logger.info("Post owner found | Post ID: "
                    + postId
                    + " | Owner ID: "
                    + ownerId);

            return ownerId;

        } catch (Exception e) {
            logger.error("Error fetching post owner | Post ID: "
                    + postId, e);
        }
        return -1;
    }

}
