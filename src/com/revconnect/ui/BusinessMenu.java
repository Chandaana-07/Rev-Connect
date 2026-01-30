package com.revconnect.ui;

import com.revconnect.model.BusinessPage;
import com.revconnect.model.BusinessPost;
import com.revconnect.model.FollowerMessage;
import com.revconnect.service.BusinessService;

import java.util.Scanner;

public class BusinessMenu {

    private Scanner sc = new Scanner(System.in);
    private BusinessService service = new BusinessService();

    public void showMenu() {
        int choice = 0;

        while (true) {
            System.out.println("\n===== BUSINESS FEATURES =====");

            boolean hasBusiness = service.hasBusiness();

            if (!hasBusiness) {
                System.out.println("1. Create Business Page");
                System.out.println("2. Back");
            } else {
                System.out.println("1. Post Update / Announcement");
                System.out.println("2. View Updates & Announcements");
                System.out.println("3. Share Promotional Content");
                System.out.println("4. View Business Page");
                System.out.println("5. View Follower Messages");
                System.out.println("6. Send Message as Follower");
                System.out.println("7. Back");
            }

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (!hasBusiness) {
                switch (choice) {
                    case 1:
                        createBusiness();
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Please create a business page first!");
                }
            } else {
                switch (choice) {
                    case 1:
                        postUpdate();
                        break;
                    case 2:
                        viewPosts();
                        break;
                    case 3:
                        sharePromotion();
                        break;
                    case 4:
                        viewBusinesses();
                        break;
                    case 5:
                        viewMessages();
                        break;
                    case 6:
                        sendMessage();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    // ---------------- METHODS ----------------

    private void createBusiness() {
        System.out.print("Enter Business Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Owner Name: ");
        String owner = sc.nextLine();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        BusinessPage page = new BusinessPage(name, owner, desc);
        service.saveBusiness(page);
    }

    private void postUpdate() {
        System.out.print("Enter Business Name: ");
        String businessName = sc.nextLine();

        System.out.print("Enter Update / Announcement: ");
        String content = sc.nextLine();

        BusinessPost post = new BusinessPost(businessName, content);
        service.savePost(post);
    }

    private void viewPosts() {
        System.out.println("\n--- Business Updates & Announcements ---");
        for (BusinessPost post : service.getAllPosts()) {
            System.out.println(post);
        }
    }

    private void sharePromotion() {
        System.out.print("Enter Promotional Message: ");
        String promo = sc.nextLine();

        System.out.println("Promotional Content Shared!");
        System.out.println("Promo: " + promo);
    }

    private void viewBusinesses() {
        System.out.println("\n--- Business Pages ---");
        for (BusinessPage b : service.getAllBusinesses()) {
            System.out.println(b);
            System.out.println("--------------------");
        }
    }

    private void viewMessages() {
        System.out.println("\n--- Follower Messages ---");
        for (FollowerMessage msg : service.getAllMessages()) {
            System.out.println(msg);
            System.out.println("------------------------");
        }
    }

    private void sendMessage() {
        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Message: ");
        String msg = sc.nextLine();

        service.saveMessage(new FollowerMessage(name, msg));
    }
}
