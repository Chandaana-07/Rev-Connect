package com.revconnect.db;

import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(DBConfiguration.URL, DBConfiguration.USER, DBConfiguration.PASS);
    }
}
