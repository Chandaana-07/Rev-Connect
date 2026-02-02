package com.revconnect.main;

import com.revconnect.ui.MainMenu;
import org.apache.log4j.Logger;


public class RevConnectApp {

    public static void main(String[] args) {
    	
    	Logger logger =
    		    Logger.getLogger(RevConnectApp.class);


        try {
            // 1. Test DB connection FIRST
            java.sql.Connection con = com.revconnect.db.DBConnection.getConnection();

            if (con != null && !con.isClosed()) {
                System.out.println(" Database connected successfully!");
                logger.info("Database is connected successfully");
                con.close();
            }

            // 2. Start Application Menu
            MainMenu menu = new MainMenu();
            logger.info("Main menu started");
            menu.showMenu();

        } catch (Exception e) {
            System.out.println(" Error starting RevConnect:");
            logger.error("Error starting RevConnect");
            e.printStackTrace();
        }
    }
}
