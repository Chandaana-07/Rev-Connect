package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.model.BusinessProduct;
import com.revconnect.model.CreatorBusinessProfile;
import com.revconnect.model.Notification;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.BusinessProductService;
import com.revconnect.service.CommentService;
import com.revconnect.service.ConnectionService;
import com.revconnect.service.CreatorBusinessService;
import com.revconnect.service.FollowService;
import com.revconnect.service.LikeService;
import com.revconnect.service.MessageService;
import com.revconnect.service.NotificationService;
import com.revconnect.service.PostService;
import com.revconnect.service.ShareService;
import com.revconnect.service.UserService;
import com.revconnect.service.PinnedPostService;
import com.revconnect.model.UserConnection;


public class UserMenu {
	private String senderName;

    private User loggedInUser;
    private Scanner sc = new Scanner(System.in);

    private PostService postService = new PostService();
    private UserService user = new UserService();
    private MessageService messageService = new MessageService();
    private CommentService commentService = new CommentService();
    private LikeService likeService = new LikeService();
    private ConnectionService connectionService = new ConnectionService();
    private ShareService shareService = new ShareService();
    private NotificationService notificationService = new NotificationService();
    private FollowService followService = new FollowService();
    private CreatorBusinessService creatorService = new CreatorBusinessService();
    private BusinessProductService productService = new BusinessProductService();
    private PinnedPostService pinnedPostService = new PinnedPostService();

    public UserMenu(User user) {
        this.loggedInUser = user;
    }

    // ===================== MAIN MENU =====================
    public void showMenu() {

        while (true) {

            int unread = notificationService.getUnreadCount(
                    loggedInUser.getUserId()
            );

            System.out.println("\n===== User Dashboard =====");
            System.out.println("1. Profile");
            System.out.println("2. Posts");
            System.out.println("3. Connections");
            System.out.println("4. Follow System");
            System.out.println("5. Messages");
            System.out.println("6. Likes & Comments");
            System.out.println("7. Notifications (" + unread + " unread)");
            System.out.println("8. Creator/Business Profile");
            System.out.println("9. Logout");

            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    profileMenu();
                    break;
                case 2:
                    postsMenu();
                    break;
                case 3:
                    connectionsMenu();
                    break;
                case 4:
                    followMenu();
                    break;
                case 5:
                    messagesMenu();
                    break;
                case 6:
                    commentsMenu();
                    break;
                case 7:
                    viewNotifications();
                    break;
                case 8:
                    creatorMenu();
                    break;
                case 9:
                    System.out.println("Logged out successfully");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== SAFE INPUT =====================
    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    // ===================== POSTS MENU =====================
    private void postsMenu() {

        while (true) {

            boolean isCreator =
                    creatorService.getProfile(loggedInUser.getUserId()) != null;

            System.out.println("\n--- Posts ---");
            System.out.println("1. Create Normal Post");

            if (isCreator) {
                System.out.println("2. Create Promotional Post");
                System.out.println("3. View My Posts");
                System.out.println("4. Edit My Post");
                System.out.println("5. Delete My Post");
                System.out.println("6. Global Feed");
                System.out.println("7. Search by Hashtag");
                System.out.println("8. Share a Post");
                System.out.println("9. View Trending Hashtags");
                System.out.println("10. Filter Feed");
                System.out.println("11. View Post Analytics");
                System.out.println("12. Back");
            } else {
                System.out.println("2. View My Posts");
                System.out.println("3. Edit My Post");
                System.out.println("4. Delete My Post");
                System.out.println("5. Global Feed");
                System.out.println("6. Search by Hashtag");
                System.out.println("7. Share a Post");
                System.out.println("8. View Trending Hashtags");
                System.out.println("9. Filter Feed");
                System.out.println("10. View Post Analytics");
                System.out.println("11. Back");
            }

            System.out.print("Choose: ");
            int choice = readInt();

            if (isCreator) {
                switch (choice) {
                    case 1: createPost(); break;
                    case 2: createPromoPost(); break;
                    case 3: viewMyPosts(); break;
                    case 4: editPost(); break;
                    case 5: deletePost(); break;
                    case 6: viewGlobalFeed(); break;
                    case 7: searchByHashtag(); break;
                    case 8: sharePost(); break;
                    case 9: viewTrendingHashtags(); break;
                    case 10: filterFeed(); break;
                    case 11: viewPostAnalytics(); break;
                    case 12: return;
                    default: System.out.println("Invalid choice");
                }
            } else {
                switch (choice) {
                    case 1: createPost(); break;
                    case 2: viewMyPosts(); break;
                    case 3: editPost(); break;
                    case 4: deletePost(); break;
                    case 5: viewGlobalFeed(); break;
                    case 6: searchByHashtag(); break;
                    case 7: sharePost(); break;
                    case 8: viewTrendingHashtags(); break;
                    case 9: filterFeed(); break;
                    case 10: viewPostAnalytics(); break;
                    case 11: return;
                    default: System.out.println("Invalid choice");
                }
            }
        }
    }

    // ===================== CREATE NORMAL POST =====================
    private void createPost() {

        System.out.print("Enter post name: ");
        String postName = sc.nextLine().trim();

        System.out.print("Enter post content: ");
        String content = sc.nextLine().trim();

        if (postName.isEmpty() || content.isEmpty()) {
            System.out.println("Post name and content cannot be empty.");
            return;
        }

        boolean success = postService.createPost(
                loggedInUser.getUserId(),
                postName,
                content,
                "NORMAL"
        );

        System.out.println(success ? "Post created successfully!" : "Failed to create post");
    }

    // ===================== CREATE PROMOTIONAL POST =====================
    private void createPromoPost() {

        CreatorBusinessProfile profile =
                creatorService.getProfile(loggedInUser.getUserId());

        if (profile == null) {
            System.out.println("Only Creator/Business accounts can create promotional posts.");
            return;
        }

        System.out.print("Enter promo post name: ");
        String postName = sc.nextLine().trim();

        System.out.print("Enter promo content: ");
        String content = sc.nextLine().trim();

        if (postName.isEmpty() || content.isEmpty()) {
            System.out.println("Post name and content cannot be empty.");
            return;
        }

        boolean success = postService.createPost(
                loggedInUser.getUserId(),
                postName,
                content,
                "PROMO"
        );

        System.out.println(success ? "Promo post created!" : "Failed to create promo post");
    }

    // ===================== VIEW MY POSTS =====================
    private void viewMyPosts() {

        List<Post> posts = postService.getPostsByUser(
                loggedInUser.getUserId()
        );

        if (posts.isEmpty()) {
            System.out.println("No posts found.");
            return;
        }

        System.out.println("\n--- My Posts ---");
        for (Post p : posts) {
            System.out.println(
                    "[" + p.getPostId() + "] " +
                            p.getUsername() + " : " +
                            p.getContent() +
                            " (" + p.getCreatedAt() + ")"
            );
        }
    }

    // ===================== EDIT POST =====================
    private void editPost() {

        System.out.print("Enter Post Name to edit: ");
        String postName = sc.nextLine().trim();

        System.out.print("Enter new content: ");
        String newContent = sc.nextLine().trim();

        if (postName.isEmpty() || newContent.isEmpty()) {
            System.out.println("Post name and new content cannot be empty.");
            return;
        }

        boolean success = postService.updatePostByName(
                loggedInUser.getUserId(),
                postName,
                newContent
        );

        System.out.println(success ? "Post updated!" : "Failed to update post.");
    }

    // ===================== DELETE POST =====================
    private void deletePost() {

        System.out.print("Enter Post Name to delete: ");
        String postName = sc.nextLine().trim();

        if (postName.isEmpty()) {
            System.out.println("Post name cannot be empty.");
            return;
        }

        boolean success = postService.deletePostByName(
                loggedInUser.getUserId(),
                postName
        );

        System.out.println(success ? "Post deleted!" : "Failed to delete post.");
    }

    // ===================== GLOBAL FEED =====================
    private void viewGlobalFeed() {

        List<Post> posts = postService.getFeedPosts(
                loggedInUser.getUserId()
        );

        if (posts.isEmpty()) {
            System.out.println("Your feed is empty.");
            return;
        }

        System.out.println("\n--- Your Feed ---");
        for (Post post : posts) {

            int likes = likeService.getLikeCount(post.getPostId());
            int shares = shareService.getShareCount(post.getPostId());

            System.out.println(
                    "[" + post.getPostId() + "] " +
                            post.getUsername() + ": " +
                            post.getContent() +
                            " Likes: " + likes +
                            " Shares: " + shares
            );
        }
    }

    // ===================== SEARCH =====================
    private void searchByHashtag() {

        System.out.print("Enter hashtag (without #): ");
        String tag = sc.nextLine().trim();

        List<Post> list = postService.searchByHashtag("#" + tag);

        if (list.isEmpty()) {
            System.out.println("No posts found.");
            return;
        }

        for (Post p : list) {
            System.out.println(p.getUsername() + ": " + p.getContent());
        }
    }

    // ===================== TRENDING =====================
    private void viewTrendingHashtags() {

        List<String> list = postService.getTrendingHashtags();

        if (list.isEmpty()) {
            System.out.println("No trending hashtags yet.");
            return;
        }

        for (String tag : list) {
            System.out.println(tag);
        }
    }

    // ===================== FILTER =====================
    private void filterFeed() {

        System.out.println("1. All");
        System.out.println("2. My Posts");
        System.out.println("3. Creators");

        int choice = readInt();

        List<Post> list = postService.getFeedPosts(
                loggedInUser.getUserId()
        );

        if (list.isEmpty()) {
            System.out.println("No posts found.");
            return;
        }

        for (Post p : list) {
            if (choice == 2 && p.getUserId() != loggedInUser.getUserId()) {
                continue;
            }
            System.out.println(p.getUsername() + ": " + p.getContent());
        }
    }

    // ===================== POST ANALYTICS =====================
    private void viewPostAnalytics() {

        System.out.print("Enter Post ID: ");
        int postId = readInt();

        Post p = postService.getPostAnalytics(postId);

        if (p == null) {
            System.out.println("Post not found.");
            return;
        }

        System.out.println("\n--- Post Analytics ---");
        System.out.println("Likes: " + likeService.getLikeCount(postId));
        System.out.println("Comments: " + commentService.getCommentsByPost(postId).size());
        System.out.println("Shares: " + shareService.getShareCount(postId));
        System.out.println("Reach: " + p.getReach());
    }

    // ===================== PROFILE =====================
    private void profileMenu() {

        System.out.println("\n--- My Profile ---");
        System.out.println("Username: " + loggedInUser.getUsername());
        System.out.println("User ID: " + loggedInUser.getUserId());
        System.out.println("Email: " + loggedInUser.getEmail());
    }

    // ===================== CREATOR MENU =====================
    private void creatorMenu() {

        while (true) {

            System.out.println("\n--- Creator / Business Profile ---");
            System.out.println("1. Register as Creator/Business");
            System.out.println("2. View My Profile");
            System.out.println("3. Update Profile");
            System.out.println("4. Manage Products/Services");
            System.out.println("5. Back");

            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1: registerCreator(); break;
                case 2: viewCreatorProfile(); break;
                case 3: updateCreatorProfile(); break;
                case 4: productMenu(); break;
                case 5: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // ===================== REGISTER CREATOR =====================
    private void registerCreator() {

        CreatorBusinessProfile p = new CreatorBusinessProfile();
        p.setUserId(loggedInUser.getUserId());

        System.out.print("Account Type (CREATOR / BUSINESS): ");
        p.setAccountType(sc.nextLine());

        System.out.print("Display Name: ");
        p.setDisplayName(sc.nextLine());

        System.out.print("Category / Industry: ");
        p.setCategory(sc.nextLine());

        System.out.print("Bio: ");
        p.setBio(sc.nextLine());

        System.out.print("Business Address: ");
        p.setAddress(sc.nextLine());

        System.out.print("Contact Info: ");
        p.setContactInfo(sc.nextLine());

        System.out.print("Website: ");
        p.setWebsite(sc.nextLine());

        System.out.print("Social Links: ");
        p.setSocialLinks(sc.nextLine());

        System.out.print("Business Hours: ");
        p.setBusinessHours(sc.nextLine());

        System.out.print("External Links: ");
        p.setExternalLinks(sc.nextLine());

        boolean success = creatorService.register(p);
        System.out.println(success ? "Profile created!" : "Failed to create profile");
    }

    // ===================== VIEW CREATOR =====================
    private void viewCreatorProfile() {

        CreatorBusinessProfile p =
                creatorService.getProfile(loggedInUser.getUserId());

        if (p == null) {
            System.out.println("No creator/business profile found.");
            return;
        }

        System.out.println("\n--- My Creator/Business Profile ---");
        System.out.println("Type: " + p.getAccountType());
        System.out.println("Name: " + p.getDisplayName());
        System.out.println("Category: " + p.getCategory());
        System.out.println("Bio: " + p.getBio());
        System.out.println("Address: " + p.getAddress());
        System.out.println("Contact: " + p.getContactInfo());
        System.out.println("Website: " + p.getWebsite());
        System.out.println("Social Links: " + p.getSocialLinks());
        System.out.println("Business Hours: " + p.getBusinessHours());
        System.out.println("External Links: " + p.getExternalLinks());
    }

    // ===================== UPDATE CREATOR =====================
    private void updateCreatorProfile() {

        CreatorBusinessProfile p =
                creatorService.getProfile(loggedInUser.getUserId());

        if (p == null) {
            System.out.println("Register first.");
            return;
        }

        System.out.print("Display Name (" + p.getDisplayName() + "): ");
        p.setDisplayName(sc.nextLine());

        System.out.print("Category (" + p.getCategory() + "): ");
        p.setCategory(sc.nextLine());

        System.out.print("Bio (" + p.getBio() + "): ");
        p.setBio(sc.nextLine());

        System.out.print("Address (" + p.getAddress() + "): ");
        p.setAddress(sc.nextLine());

        System.out.print("Contact (" + p.getContactInfo() + "): ");
        p.setContactInfo(sc.nextLine());

        System.out.print("Website (" + p.getWebsite() + "): ");
        p.setWebsite(sc.nextLine());

        System.out.print("Social Links (" + p.getSocialLinks() + "): ");
        p.setSocialLinks(sc.nextLine());

        System.out.print("Business Hours (" + p.getBusinessHours() + "): ");
        p.setBusinessHours(sc.nextLine());

        System.out.print("External Links (" + p.getExternalLinks() + "): ");
        p.setExternalLinks(sc.nextLine());

        boolean success = creatorService.update(p);
        System.out.println(success ? "Profile updated!" : "Failed to update profile");
    }

    // ===================== PRODUCTS =====================
    private void productMenu() {

        while (true) {

            System.out.println("\n--- Products / Services ---");
            System.out.println("1. Add Product/Service");
            System.out.println("2. View My Products");
            System.out.println("3. Back");

            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1: addProduct(); break;
                case 2: viewProducts(); break;
                case 3: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private void addProduct() {

        BusinessProduct p = new BusinessProduct();
        p.setUserId(loggedInUser.getUserId());

        System.out.print("Product/Service Name: ");
        p.setName(sc.nextLine());

        System.out.print("Description: ");
        p.setDescription(sc.nextLine());

        System.out.print("Price: ");
        p.setPrice(sc.nextLine());

        System.out.print("External Link: ");
        p.setLink(sc.nextLine());

        boolean success = productService.addProduct(p);
        System.out.println(success ? "Product added!" : "Failed to add product");
    }

    private void viewProducts() {

        List<BusinessProduct> list =
                productService.getProducts(loggedInUser.getUserId());

        if (list.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (BusinessProduct p : list) {
            System.out.println(
                    "[" + p.getProductId() + "] " +
                            p.getName() + " | " +
                            p.getPrice() + " | " +
                            p.getLink()
            );
        }
    }

    // ===================== NOTIFICATIONS =====================
    private void viewNotifications() {

        List<Notification> list = notificationService.getMyNotifications(
                loggedInUser.getUserId()
        );

        if (list.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (Notification n : list) {
            System.out.println(n.getMessage());
        }
    }

    // ===================== COMMENTS MENU =====================
    private void commentsMenu() {

        while (true) {

            System.out.println("\n--- Likes & Comments ---");
            System.out.println("1. Like a Post");
            System.out.println("2. Unlike a Post");
            System.out.println("3. Comment on Post");
            System.out.println("4. View Comments on Post");
            System.out.println("5. Delete My Comment");
            System.out.println("6. Respond to Comment (Creator/Business)");
            System.out.println("7. Pin Post to Profile");
            System.out.println("8. View Pinned Posts");
            System.out.println("9. Back");

            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Post ID: ");
                    likeService.likePost(
                            loggedInUser.getUserId(),
                            readInt()
                    );
                    break;

                case 2:
                    System.out.print("Enter Post ID: ");
                    boolean removed = likeService.unlikePost(
                            loggedInUser.getUserId(),
                            readInt()
                    );
                    System.out.println(removed ?
                            "Like removed." :
                            "You have not liked this post.");
                    break;

                case 3:
                    System.out.print("Enter Post ID: ");
                    int postId = readInt();

                    System.out.print("Enter comment: ");
                    String content = sc.nextLine();

                    com.revconnect.model.Comment c =
                            new com.revconnect.model.Comment();
                    c.setPostId(postId);
                    c.setUserId(loggedInUser.getUserId());
                    c.setContent(content);

                    boolean added = commentService.addComment(c);

                    System.out.println(added
                        ? "Comment added successfully!"
                        : "Failed to add comment. Please try again.");
                    break;

                case 4:
                    System.out.print("Enter Post ID: ");
                    int pid = readInt();

                    List<com.revconnect.model.Comment> list =
                            commentService.getCommentsByPost(pid);

                    if (list.isEmpty()) {
                        System.out.println("No comments yet.");
                    } else {
                        for (com.revconnect.model.Comment cm : list) {
                            System.out.println(
                                    "[" + cm.getCommentId() + "] " +
                                            cm.getUsername() + ": " +
                                            cm.getContent()
                            );
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter Comment ID to delete: ");
                    boolean success =
                            commentService.deleteComment(
                                    readInt(),
                                    loggedInUser.getUserId()
                            );

                    System.out.println(success ?
                            "Comment deleted." :
                            "You can only delete your own comments.");
                    break;

                case 6:
                    System.out.print("Enter Comment ID: ");
                    int commentId = readInt();

                    System.out.print("Enter reply: ");
                    String reply = sc.nextLine();

                    commentService.respondToComment(commentId, reply);
                    break;

                case 7:
                    System.out.print("Enter Post ID to pin: ");
                    pinnedPostService.pinPost(
                            loggedInUser.getUserId(),
                            readInt()
                    );
                    break;

                case 8:
                    for (com.revconnect.model.PinnedPost p :
                            pinnedPostService.getPinnedPosts(
                                    loggedInUser.getUserId())) {

                        System.out.println("Pinned Post ID: " +
                                p.getPostId());
                    }
                    break;

                case 9:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== SHARE =====================
    private void sharePost() {

        System.out.print("Enter Post ID to share: ");
        int postId = readInt();

        System.out.print("Enter username to share with: ");
        String targetUser = sc.nextLine().trim();

        if (targetUser.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        // Get target user from DB
        User receiver = user.getUserByUsername(targetUser);

        if (receiver == null) {
            System.out.println("User not found.");
            return;
        }

        // Save share in SHARES table
        boolean shared = shareService.sharePost(
            postId,
            loggedInUser.getUserId()
        );

        if (shared) {
            System.out.println("Post shared successfully with " + targetUser);
        } else {
            System.out.println("Failed to share post.");
        }
    }

    // ===================== CONNECTIONS =====================
    private void connectionsMenu() {

        while (true) {

            System.out.println("\n--- Connections ---");
            System.out.println("1. Send Connection Request");
            System.out.println("2. View Pending Requests");
            System.out.println("3. Accept Request");
            System.out.println("4. Reject Request");
            System.out.println("5. View My Connections");
            System.out.println("6. Remove Connection");
            System.out.println("7. Back");

            System.out.print("Choose: ");
            int choice = readInt();
            int targetId;

            switch (choice) {

            case 1: {
                System.out.print("Enter Username to send request: ");
                String targetUsername = sc.nextLine().trim();

                User receiver = user.getUserByUsername(targetUsername);

                if (receiver == null) {
                    System.out.println("User not found.");
                    break;
                }

                int senderId = loggedInUser.getUserId();
                String senderName = loggedInUser.getUsername();

                int receiverId = receiver.getUserId();
                String receiverName = receiver.getUsername();

                boolean success = connectionService.sendRequest(
                    senderId,
                    receiverId,
                    senderName,
                    receiverName
                );

                System.out.println(success
                    ? "Connection request sent!"
                    : "Failed to send request.");
                break;
            }

            case 2: {
                System.out.println("\n--- Pending Requests ---");

                int loggedInUserId = loggedInUser.getUserId();

                List<UserConnection> pending =
                    connectionService.getPendingRequests(loggedInUserId);

                if (pending == null || pending.isEmpty()) {
                    System.out.println("No pending requests.");
                } else {
                    System.out.printf("%-5s %-15s %-25s%n", "ID", "Sender", "Date");
                    System.out.println("-----------------------------------------------");

                    for (UserConnection c : pending) {
                        System.out.printf(
                            "%-5d %-15s %-25s%n",
                            c.getConnectionId(),
                            c.getSenderName(),
                            String.valueOf(c.getCreatedAt())
                        );
                    }
                }
                break;
            }

            case 3: {
                System.out.println("\n--- Accept Request ---");

                int receiverId = loggedInUser.getUserId();

                System.out.print("Enter Connection ID to accept: ");
                int connectionId = Integer.parseInt(sc.nextLine().trim());

                boolean accepted = connectionService.acceptRequest(
                    connectionId,
                    receiverId
                );

                System.out.println(accepted
                    ? "Request accepted!"
                    : "Request not found or already processed.");
                break;
            }

            case 4: {
                System.out.println("\n--- Reject Request ---");

                int receiverId = loggedInUser.getUserId();

                System.out.print("Enter Connection ID to reject: ");
                int connectionId = Integer.parseInt(sc.nextLine().trim());

                boolean rejected = connectionService.rejectRequest(
                    connectionId,
                    receiverId
                );

                System.out.println(rejected
                    ? "Request rejected!"
                    : "Request not found or already processed.");
                break;
            }

            case 5: {
                System.out.println("\n--- My Connections ---");

                for (UserConnection c :
                        connectionService.getConnections(loggedInUser.getUserId())) {

                    int connectedUser =
                        (c.getSenderId() == loggedInUser.getUserId())
                            ? c.getReceiverId()
                            : c.getSenderId();

                    System.out.println("Connected with User ID: " + connectedUser);
                }
                break;
            }

            case 6: {
                System.out.println("\n--- Remove Connection ---");

                System.out.print("Enter Connection ID to remove: ");
                int connectionId = Integer.parseInt(sc.nextLine().trim());

                boolean removed = connectionService.removeConnection(
                    connectionId,
                    loggedInUser.getUserId()
                );

                System.out.println(removed
                    ? "Connection removed!"
                    : "Connection not found or not accepted.");
                break;
            }

            case 7:
                return;

            default:
                System.out.println("Invalid choice");
        }
        
        }
    }
    // ===================== FOLLOW =====================
    private void followMenu() {

        while (true) {

            System.out.println("\n--- Follow System ---");

            System.out.println("Total Followers: " +
                    connectionService.getFollowerCount(
                            loggedInUser.getUserId()));

            System.out.println("1. Follow User");
            System.out.println("2. Unfollow User");
            System.out.println("3. View Followers");
            System.out.println("4. View Following");
            System.out.println("5. Back");

            System.out.print("Choose: ");
            int choice = readInt();
            int targetId;

            switch (choice) {

            case 1:
                System.out.print("Enter User ID to follow: ");
                int id = readInt();

                boolean followed = connectionService.follow(loggedInUser.getUserId(), id);

                if (followed) {
                    System.out.println("Followed user successfully!");
                } else {
                    System.out.println("You are already following this user or follow failed.");
                }
                break;

            case 2:
                System.out.print("Enter User ID to unfollow: ");
                targetId = readInt();

                boolean unfollowed = connectionService.unfollow(
                        loggedInUser.getUserId(), targetId
                );

                if (unfollowed) {
                    System.out.println("Unfollowed successfully!");
                } else {
                    System.out.println("You are not following this user or unfollow failed.");
                }
                break;


            case 3:
                System.out.println("\n--- My Followers ---");

                List<com.revconnect.model.UserConnection> followers =
                        connectionService.getFollowers(loggedInUser.getUserId());

                if (followers == null || followers.isEmpty()) {
                    System.out.println("You have no followers yet.");
                } else {
                    for (com.revconnect.model.UserConnection c : followers) {
                        System.out.println("Follower User ID: " + c.getSenderId());
                    }
                }
                break;
                
                
         
            case 4:
                System.out.println("\n--- I Am Following ---");

                List<com.revconnect.model.UserConnection> following =
                        connectionService.getFollowing(loggedInUser.getUserId());

                if (following == null || following.isEmpty()) {
                    System.out.println("You are not following anyone yet.");
                } else {
                    for (com.revconnect.model.UserConnection c : following) {
                        System.out.println("Following User ID: " + c.getReceiverId());
                    }
                }
                break;


                case 5:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== MESSAGES =====================
    private void messagesMenu() {

        while (true) {
            System.out.println("\n--- Messages ---");
            System.out.println("1. Send Message");
            System.out.println("2. View Inbox");
            System.out.println("3. Back");

            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter username to send message: ");
                    String target = sc.nextLine().trim();

                    User receiver =
                        user.getUserByUsername(target);

                    if (receiver == null) {
                        System.out.println("User not found.");
                        break;
                    }

                    System.out.print("Enter message: ");
                    String content = sc.nextLine();

                    boolean sent =
                        messageService.sendMessage(
                            loggedInUser.getUserId(),
                            receiver.getUserId(),
                            content
                        );

                    System.out.println(sent
                        ? "Message sent!"
                        : "Failed to send message.");
                    break;

                case 2:
                    System.out.println("\n--- Inbox ---");

                    List<com.revconnect.model.Message> inbox =
                        messageService.getInbox(
                            loggedInUser.getUserId()
                        );

                    if (inbox == null || inbox.isEmpty()) {
                        System.out.println("No messages.");
                    } else {
                        for (com.revconnect.model.Message m : inbox) {
                            System.out.println(
                                "From User ID: " +
                                m.getSenderId() +
                                " | " +
                                m.getContent() +
                                " (" + m.getCreatedAt() + ")"
                            );
                        }
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

}
