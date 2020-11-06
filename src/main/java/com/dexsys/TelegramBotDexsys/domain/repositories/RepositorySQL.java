package com.dexsys.TelegramBotDexsys.domain.repositories;

import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.domain.repositories.DTO.UserDbDTO;
import com.dexsys.TelegramBotDexsys.services.IConnectionFactory;
import com.dexsys.TelegramBotDexsys.services.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
public class RepositorySQL implements IRepository {

    @Autowired
    private IConnectionFactory connectionFactory;
    //записывает пользователя в БД
    //если данный пользователь есть в БД - обновляет его определенные поля
    //проверка на наличие пользователя - по chatId
    @Override
    public Integer addUser(UserWebDTO userWebDTO) {
        String query = getInsertQuery();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connectionFactory.createStatement(connection, query)) {
            connection.setAutoCommit(false);
            statement.setString(1, userWebDTO.getBirthDate().toString());
            statement.setString(2, userWebDTO.getChatId());
            statement.setString(3, userWebDTO.getFirstName());
            statement.setString(4, userWebDTO.getSecondName());
            statement.setString(5, userWebDTO.getMiddleName());
            statement.setObject(6, userWebDTO.getId());
            statement.setBoolean(7, userWebDTO.isMale());
            statement.setString(8, userWebDTO.getPhone());
            statement.setString(9, userWebDTO.getBirthDate().toString());
            int i = connectionFactory.executeInsertStatement(statement);
            connection.commit();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception");
        }
    }

    @Override
    public List<UserDbDTO> getUserList()  {
        String query = getUsersQuery();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connectionFactory.createStatement(connection, query)) {
            //setAutoCommit(true) - автоматически коммитит каждый сделанынй statement в подключенную БД
            //setAutoCommit(false) - копит все сделанные statement и каоммити все сразу , при команде connection.commit();
            try {
                connection.setAutoCommit(false);
                resultSet = connectionFactory.executeSelectStatement(statement);
                connection.commit();
                return processUsersResultSet(resultSet);
            } finally {
                if (resultSet != null) resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception");
        }
    }

    //получает данные по пользователю (по его chatId)
    //получает пустой ResultSEt если пользователя с таким chatId нет в базе
    @Override
    public UserDbDTO getUser(String chatId) {
        String query = getUserQuery();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connectionFactory.createStatement(connection, query)) {
            //setAutoCommit(true) - автоматически коммитит каждый сделанынй statement в подключенную БД
            //setAutoCommit(false) - копит все сделанные statement и каоммити все сразу , при команде connection.commit();
            try {
                connection.setAutoCommit(false);
                statement.setString(1, chatId);
                resultSet = connectionFactory.executeSelectStatement(statement);
                connection.commit();
                return processUserResultSet(resultSet);
            } finally {
                if (resultSet != null) resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception");
        }
    }

    @Override
    public Integer deleteUser(String chatId) {
        String query = deleteQuery();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connectionFactory.createStatement(connection, query)) {
            //setAutoCommit(true) - автоматически коммитит каждый сделанынй statement в подключенную БД
            //setAutoCommit(false) - копит все сделанные statement и каоммити все сразу , при команде connection.commit();
            connection.setAutoCommit(false);
            statement.setObject(1, chatId);
            int i =  connectionFactory.executeInsertStatement(statement);
            connection.commit();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException("Exception");
        }
    }

    //запрос для insertUser
    private String getInsertQuery() {
        return "INSERT into users (birthdate, chatid, firstname, secondname, middlename, id, ismale, phone)" +
                " values (?,?,?,?,?,?,?,?) ON CONFLICT (chatid) DO UPDATE SET birthdate = ?";
    }

    //запрос для getUser
    private String getUserQuery() {
        return "SELECT * FROM users where chatid = ?";
    }

    private String getUsersQuery() {
        return "SELECT * FROM users";
    }

    private String deleteQuery() {
        return "DELETE FROM users where chatid = ?";
    }

    //обработка resultSet в UserDTO (для метода getUser)
    private UserDbDTO processUserResultSet(ResultSet resultSet) throws SQLException {
        //если вернуло данные в resultSet
        if (resultSet.next()) {
            return mapToUserDbDTO.apply(resultSet);
        } else {
            return null;
        }
    }

    private List<UserDbDTO> processUsersResultSet(ResultSet resultSet) throws SQLException {
        List<UserDbDTO> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(mapToUserDbDTO.apply(resultSet));
        }
        return userList;
    }

    private  Function<ResultSet, UserDbDTO>  mapToUserDbDTO  =  it -> {
        try {
            return UserDbDTO.builder()
                    .birthDate(it.getString(1))
                    .chatId(it.getString(2))
                    .firstName(it.getString(3))
                    .secondName(it.getString(4))
                    .middleName(it.getString(5))
                    .id((UUID) it.getObject(6))
                    .isMale(it.getBoolean(7))
                    .phone(it.getString(8))
                    .build();
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
    };
}
