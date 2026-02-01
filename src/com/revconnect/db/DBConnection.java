package com.revconnect.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Oracle 10g XE uses SID, not service name
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
    private static final String USER = "system";
    private static final String PASSWORD = "chandana"; // MUST match sqlplus password

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
