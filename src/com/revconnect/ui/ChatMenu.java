package com.revconnect.ui;

import com.revconnect.model.Message;
import com.revconnect.model.User;
import com.revconnect.service.MessageService;

import java.util.Scanner;

public class ChatMenu {

    private Scanner sc = new Scanner(System.in);
    private MessageService service = new MessageService();
    private User user;

    public ChatMenu(User user) {
        this.user = user;
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

            switch (choice) {
                case 1:
                    send();
                    break;
                case 2:
                    view();
                    break;
            }
        } while (choice != 3);
    }

    private void send() {
        System.out.print("Enter Receiver User ID: ");
        int toId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Message: ");
        String msg = sc.nextLine();

        service.send(new Message(user.getUserId(), toId, msg));
        System.out.println("Message Sent!");
    }

    private void view() {
        System.out.print("Enter User ID to View Chat: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Message m :
                service.getConversation(user.getUserId(), id)) {
            System.out.println(m);
        }

        service.markRead(user.getUserId());
    }
}
