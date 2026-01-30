package com.revconnect.ui;

import com.revconnect.model.Notification;
import com.revconnect.model.User;
import com.revconnect.service.NotificationService;

import java.util.Scanner;

public class NotificationMenu {

    private Scanner sc = new Scanner(System.in);
    private NotificationService service = new NotificationService();
    private User user;

    public NotificationMenu(User user) {
        this.user = user;
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n===== NOTIFICATIONS =====");
            System.out.println("1. View Notifications");
            System.out.println("2. Mark All as Read");
            System.out.println("3. Back");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    view();
                    break;
                case 2:
                    service.markAllRead(user.getUserId());
                    System.out.println("All notifications marked as read");
                    break;
            }
        } while (choice != 3);
    }

    private void view() {
        for (Notification n :
                service.getUserNotifications(user.getUserId())) {
            System.out.println(n);
        }
    }
}
