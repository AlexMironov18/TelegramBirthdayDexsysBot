package com.dexsys.TelegramBotDexsys.domain.repositories;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ConnectionFactoryPostgeSQL implements IConnectionFactory {

    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/telegramdb";
    static final String NAME = "postgres";
    static final String PASSWORD = "123";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, NAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("connction exception");
        }
    }

    @Override
    public PreparedStatement createStatement(Connection connection, String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throw new RuntimeException("statement exception");
        }
    }

    @Override
    public Integer executeInsertStatement(PreparedStatement statement) {
        try {
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("statement exception");
        }
    }

    @Override
    public ResultSet executeSelectStatement(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("statement exception");
        }
    }

}
