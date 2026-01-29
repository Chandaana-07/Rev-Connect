package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.model.Comment;
import com.revconnect.model.Message;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.CommentService;
import com.revconnect.service.LikeService;
import com.revconnect.service.MessageService;
import com.revconnect.service.PostService;
import com.revconnect.service.UserService;
import com.revconnect.service.ShareService;


public class UserMenu {

    private User loggedInUser;
    private PostService postService = new PostService();
    private UserService userService = new UserService();
    private MessageService messageService = new MessageService();
    private CommentService commentService = new CommentService();
    private LikeService likeService = new LikeService();
    private ShareService shareService = new ShareService();

    public UserMenu(User user) {
        this.loggedInUser = user;
    }

    // ===================== MAIN MENU =====================
    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== User Dashboard =====");
            System.out.println("1. View My Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Create Post");
            System.out.println("4. View My Posts");
            System.out.println("5. Global Feed");
            System.out.println("6. View Other User Profile");
            System.out.println("7. Messages");
            System.out.println("8. Comments");
            System.out.println("9. Like / Unlike Post");
            System.out.println("10. Notifications");
            System.out.println("11. Share Post");
            System.out.println("12. Logout");

            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    viewProfile();
                    break;

                case 2:
                    editProfile(sc);
                    break;

                case 3:
                    createPost(sc);
                    break;

                case 4:
                    viewMyPosts();
                    break;

                case 5:
                    viewGlobalFeed();
                    break;

                case 6:
                    viewOtherUserProfile(sc);
                    break;

                case 7:
                    messagesMenu(sc);
                    break;

                case 8:
                    commentsMenu(sc);
                    break;

                case 9:
                    likeMenu(sc);
                    break;

                case 10:
                    viewNotifications(sc);
                    break;

                case 11:
                    sharePost(sc);
                    break;

                case 12:
                    System.out.println("Logged out successfully");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== PROFILE =====================
    private void viewProfile() {

        if (loggedInUser == null) {
            System.out.println("Please login first to view your profile.");
            return;
        }

        System.out.println("\n--- My Profile ---");
        System.out.println("Username : " + loggedInUser.getUsername());
        System.out.println("Email    : " + loggedInUser.getEmail());
        System.out.println("Bio      : " + loggedInUser.getBio());
        System.out.println("Location : " + loggedInUser.getLocation());
        System.out.println("Website  : " + loggedInUser.getWebsite());
    }

    private void editProfile(Scanner sc) {

        System.out.print("Enter Bio: ");
        loggedInUser.setBio(sc.nextLine());

        System.out.print("Enter Location: ");
        loggedInUser.setLocation(sc.nextLine());

        System.out.print("Enter Website: ");
        loggedInUser.setWebsite(sc.nextLine());

        boolean updated = userService.updateProfile(loggedInUser);

        if (updated) {
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Profile update failed");
        }
    }

    // ===================== POSTS =====================
    private void createPost(Scanner sc) {

        Post post = new Post();
        post.setUserId(loggedInUser.getUserId());

        System.out.print("Enter post content: ");
        String content = sc.nextLine().trim();

        if (content.length() == 0) {
            System.out.println("Post cannot be empty.");
            return;
        }

        post.setContent(content);

        boolean success = postService.createPost(post);

        if (success) {
            System.out.println("Post created successfully!");
        } else {
            System.out.println("Failed to create post");
        }
    }


    private void viewMyPosts() {

        if (loggedInUser == null) {
            System.out.println("Please login first.");
            return;
        }

        List<Post> posts =
            postService.getPostsByUser(loggedInUser.getUserId());

        if (posts.isEmpty()) {
            System.out.println("No posts found.");
            return;
        }

        System.out.println("\n--- My Posts ---");
        for (Post post : posts) {
            System.out.println(
                post.getContent() +
                " (" + post.getCreatedAt() + ")"
            );
        }
    }

    // ===================== GLOBAL FEED =====================
    private void viewGlobalFeed() {

        List<Post> posts = postService.getAllPosts();

        System.out.println("\n--- Global Feed ---");
        for (Post post : posts) {

            int likeCount = likeService.getLikeCount(post.getPostId());
            int shareCount = shareService.getShareCount(post.getPostId());

            System.out.println(
                post.getUsername() + ": " +
                post.getContent() +
                " (" + post.getCreatedAt() + ") " +
                "Likes: " + likeCount +
                " Shares: " + shareCount
            );
        }
    }


    // ===================== VIEW OTHER USER =====================
    private void viewOtherUserProfile(Scanner sc) {

        System.out.print("Enter username to search: ");
        String username = sc.nextLine();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\n--- User Profile ---");
        System.out.println("Username : " + user.getUsername());
        System.out.println("Bio      : " + user.getBio());
        System.out.println("Location : " + user.getLocation());
        System.out.println("Website  : " + user.getWebsite());
    }

    // ===================== MESSAGES MENU =====================
    private void messagesMenu(Scanner sc) {

        while (true) {
            System.out.println("\n--- Messages ---");
            System.out.println("1. Send Message");
            System.out.println("2. View Inbox");
            System.out.println("3. View Conversation");
            System.out.println("4. Back");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    sendMessage(sc);
                    break;

                case 2:
                    viewInbox();
                    break;

                case 3:
                    viewConversation(sc);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== SEND MESSAGE =====================
    private void sendMessage(Scanner sc) {

        System.out.print("Enter receiver username: ");
        String username = sc.nextLine().trim();

        if (username.length() == 0) {
            System.out.println("Username cannot be empty.");
            return;
        }

        User receiver = userService.getUserByUsername(username);

        if (receiver == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter message: ");
        String content = sc.nextLine().trim();

        if (content.length() == 0) {
            System.out.println("Message cannot be empty.");
            return;
        }

        Message msg = new Message();
        msg.setSenderId(loggedInUser.getUserId());
        msg.setReceiverId(receiver.getUserId());
        msg.setContent(content);

        boolean success = messageService.sendMessage(msg);

        if (success) {
            System.out.println("Message sent!");
        } else {
            System.out.println("Failed to send message");
        }
    }


    // ===================== VIEW INBOX =====================
    private void viewInbox() {

        List<Message> inbox =
            messageService.getInbox(loggedInUser.getUserId());

        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
            return;
        }

        System.out.println("\n--- Inbox ---");
        for (Message msg : inbox) {
            System.out.println(
                "From User ID " + msg.getSenderId() +
                ": " + msg.getContent() +
                " (" + msg.getSentAt() + ")"
            );
        }
    }

    // ===================== VIEW CONVERSATION =====================
    private void viewConversation(Scanner sc) {

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Message> convo =
            messageService.getConversation(
                loggedInUser.getUserId(),
                user.getUserId()
            );

        if (convo.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }

        System.out.println("\n--- Conversation ---");
        for (Message msg : convo) {

            String who =
                msg.getSenderId() == loggedInUser.getUserId()
                    ? "You"
                    : user.getUsername();

            System.out.println(
                who + ": " +
                msg.getContent() +
                " (" + msg.getSentAt() + ")"
            );
        }
    }

    // ===================== COMMENTS MENU =====================
    private void commentsMenu(Scanner sc) {

        while (true) {
            System.out.println("\n--- Comments ---");
            System.out.println("1. Add Comment");
            System.out.println("2. View Comments on a Post");
            System.out.println("3. Delete My Comment");
            System.out.println("4. Back");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addComment(sc);
                    break;

                case 2:
                    viewComments(sc);
                    break;

                case 3:
                    deleteComment(sc);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== ADD COMMENT =====================
    private void addComment(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter comment: ");
        String content = sc.nextLine().trim();

        if (content.length() == 0) {
            System.out.println("Comment cannot be empty.");
            return;
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(loggedInUser.getUserId());
        comment.setContent(content);

        boolean success = commentService.addComment(comment);

        if (success) {
            System.out.println("Comment added!");
        } else {
            System.out.println("Failed to add comment");
        }
    }


    // ===================== VIEW COMMENTS =====================
    private void viewComments(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = Integer.parseInt(sc.nextLine());

        List<Comment> comments =
            commentService.getCommentsByPost(postId);

        if (comments.isEmpty()) {
            System.out.println("No comments found.");
            return;
        }

        System.out.println("\n--- Comments ---");
        for (Comment c : comments) {
            System.out.println(
                c.getUsername() + ": " +
                c.getContent() +
                " (" + c.getCreatedAt() + ")"
            );
        }
    }

    // ===================== DELETE COMMENT =====================
    private void deleteComment(Scanner sc) {

        System.out.print("Enter Comment ID: ");
        int commentId = Integer.parseInt(sc.nextLine());

        boolean success =
            commentService.deleteComment(
                commentId,
                loggedInUser.getUserId()
            );

        if (success) {
            System.out.println("Comment deleted!");
        } else {
            System.out.println("Failed to delete comment (Check ID)");
        }
    }

    // ===================== LIKES MENU =====================
    private void likeMenu(Scanner sc) {

        while (true) {
            System.out.println("\n--- Likes ---");
            System.out.println("1. Like a Post");
            System.out.println("2. Unlike a Post");
            System.out.println("3. Back");

            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    likePost(sc);
                    break;

                case 2:
                    unlikePost(sc);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // ===================== LIKE POST =====================
    private void likePost(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = Integer.parseInt(sc.nextLine());

        boolean success =
            likeService.likePost(
                postId,
                loggedInUser.getUserId()
            );

        if (success) {
            System.out.println("Post liked!");
        } else {
            System.out.println("Already liked or invalid post.");
        }
    }

    // ===================== UNLIKE POST =====================
    private void unlikePost(Scanner sc) {

        System.out.print("Enter Post ID: ");
        int postId = Integer.parseInt(sc.nextLine());

        boolean success =
            likeService.unlikePost(
                postId,
                loggedInUser.getUserId()
            );

        if (success) {
            System.out.println("Like removed!");
        } else {
            System.out.println("You have not liked this post.");
        }
    }
 // ===================== NOTIFICATIONS =====================
    private void viewNotifications(Scanner sc) {

        List<com.revconnect.model.Notification> list =
            new com.revconnect.service.NotificationService()
                .getMyNotifications(
                    loggedInUser.getUserId()
                );

        if (list.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        System.out.println("\n--- Notifications ---");
        for (com.revconnect.model.Notification n : list) {
            System.out.println(
                "[" + n.getNotifId() + "] " +
                n.getMessage() +
                " (" + n.getCreatedAt() + ")"
            );
        }

        System.out.print(
            "Enter Notification ID to mark as read (0 to skip): "
        );
        int id = Integer.parseInt(sc.nextLine());

        if (id != 0) {
            new com.revconnect.service.NotificationService()
                .markAsRead(
                    id,
                    loggedInUser.getUserId()
                );
            System.out.println("Marked as read.");
        }
    }

    //==========================SHARE===================================
    private void sharePost(Scanner sc) {

        System.out.print("Enter Post ID to share: ");
        int postId = Integer.parseInt(sc.nextLine());

        boolean success =
            shareService.sharePost(
                postId,
                loggedInUser.getUserId()
            );

        if (success) {
            System.out.println("Post shared successfully!");
        } else {
            System.out.println("Failed to share post");
        }
    }

}
