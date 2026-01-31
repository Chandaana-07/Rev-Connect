package com.revconnect.db;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection() throws Exception;
}
