package com.revconnect.main;

import com.revconnect.ui.MainMenu;

public class RevConnectApp {

    public static void main(String[] args) {

        try {
            // 1. Test DB connection FIRST
            java.sql.Connection con = com.revconnect.db.DBConnection.getConnection();

            if (con != null && !con.isClosed()) {
                System.out.println(" Database connected successfully!");
                con.close();
            }

            // 2. Start Application Menu
            MainMenu menu = new MainMenu();
            menu.showMenu();

        } catch (Exception e) {
            System.out.println(" Error starting RevConnect:");
            e.printStackTrace();
        }
    }
}
