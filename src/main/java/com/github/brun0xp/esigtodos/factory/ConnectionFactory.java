package com.github.brun0xp.esigtodos.factory;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;
    private static final String URL = "jdbc:postgresql://ec2-3-220-23-212.compute-1.amazonaws.com:5432/dcrhp7ltbabjpm";
    private static final String USERNAME = "soimlmqgwipnqj";
    private static final String PASSWORD = "f0ada4d1b09fb93402331e0dff3391670fd35550f19c8b33acc366619f54cfc6";

    @SneakyThrows
    public static Connection getConnection() {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }

        return connection;
    }

    @SneakyThrows
    public static void closeConnection() {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
