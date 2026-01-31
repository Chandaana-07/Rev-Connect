package com.revconnect.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con;

    public static synchronized Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                con = DriverManager.getConnection(
                    DBConfiguration.URL,
                    DBConfiguration.USER,
                    DBConfiguration.PASS
                );
            }
        } catch (Exception e) {
            System.out.println("Unable to connect to database.");
        }
        return con;
    }
}
