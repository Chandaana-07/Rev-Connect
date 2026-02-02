package com.revconnect.ui;
import org.apache.log4j.Logger;

import com.revconnect.model.Message;
import com.revconnect.model.User;
import com.revconnect.service.MessageService;

import java.util.Scanner;

public class ChatMenu {

	private static final Logger logger =
            Logger.getLogger(ChatMenu.class);
    private Scanner sc = new Scanner(System.in);
    private MessageService service = new MessageService();
    private User user;

    public ChatMenu(User user) {
        this.user = user;
        logger.info("ChatMenu opened for user ID: " + user.getUserId());

    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== CHAT MENU =====");
            System.out.println("1. Send Message");
            System.out.println("2. View Conversation");
            System.out.println("3. Back");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();

            logger.info("Chat menu choice selected: " + choice);
            switch (choice) {
            case 1:
                logger.info("Selected: Send Message");
                send();
                break;
            case 2:
                logger.info("Selected: View Conversation");
                view();
                break;
            case 3:
                logger.info("Exited ChatMenu for user ID: " + user.getUserId());
                break;
            default:
                logger.warn("Invalid chat menu choice: " + choice);
        }
        } while (choice != 3);
    }

    private void send() {
    	 try {
             System.out.print("Enter Receiver User ID: ");
             int toId = sc.nextInt();
             sc.nextLine();

             System.out.print("Enter Message: ");
             String msg = sc.nextLine();

             logger.info("Sending message from user " + user.getUserId()
                     + " to user " + toId);

             service.send(new Message(user.getUserId(), toId, msg));

             logger.info("Message successfully sent");

             System.out.println("Message Sent!");
         } catch (Exception e) {
             logger.error("Error while sending message", e);
         }
    }

    private void view() {
    	 try {
             System.out.print("Enter User ID to View Chat: ");
             int id = sc.nextInt();
             sc.nextLine();

             logger.info("Viewing conversation between user "
                     + user.getUserId() + " and user " + id);

             for (Message m :
                     service.getConversation(user.getUserId(), id)) {
                 System.out.println(m);
             }

             service.markRead(user.getUserId());
             logger.info("Marked messages as read for user ID: "
                     + user.getUserId());

         } catch (Exception e) {
             logger.error("Error while viewing conversation", e);
         }
}
}
