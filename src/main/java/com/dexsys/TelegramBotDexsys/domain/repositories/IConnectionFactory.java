package com.dexsys.TelegramBotDexsys.domain.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IConnectionFactory {
    Connection getConnection();
    PreparedStatement createStatement(Connection connection, String query);
    Integer executeInsertStatement(PreparedStatement statement);
    ResultSet executeSelectStatement(PreparedStatement statement);
}
