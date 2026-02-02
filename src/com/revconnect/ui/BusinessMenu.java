package com.revconnect.ui;

import com.revconnect.model.BusinessPage;
import com.revconnect.model.BusinessPost;
import com.revconnect.model.FollowerMessage;
import com.revconnect.service.BusinessService;

import org.apache.log4j.Logger;
import java.util.Scanner;

public class BusinessMenu {

    private Scanner sc = new Scanner(System.in);
    private BusinessService service = new BusinessService();
    Logger logger =
            Logger.getLogger(BusinessMenu.class);

    public void showMenu() {
        int choice = 0;
        logger.info("Entered Business Menu");

        while (true) {
            System.out.println("\n===== BUSINESS FEATURES =====");

            boolean hasBusiness = service.hasBusiness();
            logger.info("Business page exists: " + hasBusiness);

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
            
            logger.info("Business menu choice selected: " + choice);

            if (!hasBusiness) {
                switch (choice) {
                    case 1:
                    	logger.info("Selected Create Business Page");
                        createBusiness();
                        break;
                    case 2:
                    	logger.info("Exited Business Menu");
                        return;
                    default:
                    	logger.warn("Invalid choice before business creation: " + choice);
                        System.out.println("Please create a business page first!");
                }
            } else {
                switch (choice) {
                    case 1:
                    	logger.info("Selected Post Update / Announcement");
                        postUpdate();
                        break;
                    case 2:
                    	logger.info("Selected View Updates & Announcements");
                        viewPosts();
                        break;
                    case 3:
                    	logger.info("Selected Share Promotional Content");
                        sharePromotion();
                        break;
                    case 4:
                    	logger.info("Selected View Business Page");
                        viewBusinesses();
                        break;
                    case 5:
                    	logger.info("Selected View Follower Messages");
                        viewMessages();
                        break;
                    case 6:
                    	logger.info("Selected Send Message as Follower");
                        sendMessage();
                        break;
                    case 7:
                    	logger.info("Exited Business Menu");
                        return;
                    default:
                    	logger.warn("Invalid business menu choice: " + choice);
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    // ---------------- METHODS ----------------

    private void createBusiness() {
    	try{
        System.out.print("Enter Business Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Owner Name: ");
        String owner = sc.nextLine();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();
        
        logger.info("Creating business page: " + name + " | Owner: " + owner);

        BusinessPage page = new BusinessPage(name, owner, desc);
        service.saveBusiness(page);
        
        logger.info("Business page saved: " + name);
        } catch (Exception e) {
            logger.error("Error while creating business page", e);
        }
    
        
    }

    private void postUpdate() {
    	try{
        System.out.print("Enter Business Name: ");
        String businessName = sc.nextLine();

        System.out.print("Enter Update / Announcement: ");
        String content = sc.nextLine();
        
        logger.info("Posting update for business: " + businessName);

        BusinessPost post = new BusinessPost(businessName, content);
        service.savePost(post);
        
        logger.info("Business post saved for: " + businessName);
        } catch (Exception e) {
            logger.error("Error while posting business update", e);
        }
    }

    private void viewPosts() {
    	try {
            System.out.println("\n--- Business Updates & Announcements ---");
            logger.info("Viewing all business posts");

            for (BusinessPost post : service.getAllPosts()) {
                System.out.println(post);
            }
        } catch (Exception e) {
            logger.error("Error while viewing business posts", e);
        }
    }

    private void sharePromotion() {
        try {
            System.out.print("Enter Promotional Message: ");
            String promo = sc.nextLine();

            logger.info("Promotional content shared: " + promo);

            System.out.println("Promotional Content Shared!");
            System.out.println("Promo: " + promo);
        } catch (Exception e) {
            logger.error("Error while sharing promotional content", e);
        }
    }

    private void viewBusinesses() {
    	 try {
             System.out.println("\n--- Business Pages ---");
             logger.info("Viewing all business pages");

             for (BusinessPage b : service.getAllBusinesses()) {
                 System.out.println(b);
                 System.out.println("--------------------");
             }
         } catch (Exception e) {
             logger.error("Error while viewing business pages", e);
         }
    }

    private void viewMessages() {
    	try {
            System.out.println("\n--- Follower Messages ---");
            logger.info("Viewing follower messages");

            for (FollowerMessage msg : service.getAllMessages()) {
                System.out.println(msg);
                System.out.println("------------------------");
            }
        } catch (Exception e) {
            logger.error("Error while viewing follower messages", e);
        }
    }

    private void sendMessage() {
    	 try {
             System.out.print("Enter Your Name: ");
             String name = sc.nextLine();

             System.out.print("Enter Message: ");
             String msg = sc.nextLine();

             logger.info("Follower message sent by: " + name);

             service.saveMessage(new FollowerMessage(name, msg));

             logger.info("Follower message saved for business");
         } catch (Exception e) {
             logger.error("Error while sending follower message", e);
         }
    }
}
