package com.mobin.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Mobin on 2017/9/12.
 */
public class DatabaseConnection {
    Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String strURL = "jdbc:postgresql://localhost:5432/grab_dev";
    private static final String USER = "postgres";
    private static final String PASSWD = "154283";
    private Connection conn;

    public DatabaseConnection(){
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(strURL, USER, PASSWD);
        } catch (Exception e) {
            log.error("数据库连接失败！" + e);
        }
    }

    public Connection getConnection(){
        return conn;
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
