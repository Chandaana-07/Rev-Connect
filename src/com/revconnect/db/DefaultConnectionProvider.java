package com.revconnect.db;

import java.sql.Connection;

public class DefaultConnectionProvider implements ConnectionProvider {

    @Override
    public Connection getConnection() throws Exception {
        return DBConnection.getConnection();
    }
}
