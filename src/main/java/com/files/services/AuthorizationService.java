package com.files.services;

import com.files.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationService {
    private final Connection _connection;
    private final Map<String, User> _userBySessionKeyMap = new HashMap<>();

    public AuthorizationService(Connection connection) {
        _connection = connection;
    }

    public void register(User user) throws Exception {
        User existedUser = getUserByLogin(user.getLogin());
        if (existedUser != null) {
            throw new Exception("This login already exists");
        }

        try (Statement statement = _connection.createStatement()) {
            statement.execute(String.format(
                    "insert into files.users (Login, Email, Password) values ('%s', '%s', '%s')",
                    user.getLogin(),
                    user.getEmail(),
                    user.getPassword()));
        }
    }

    public boolean isLogin(String sessionKey) {
        return _userBySessionKeyMap.containsKey(sessionKey);
    }

    public void login(String sessionKey, User user) throws RuntimeException, SQLException {
        if (!containsUser(user)) {
            throw new RuntimeException("User not exists");
        }

        if (!_userBySessionKeyMap.containsKey(sessionKey)) {
            _userBySessionKeyMap.put(sessionKey, user);
        }
    }

    public String getLogin(String sessionKey) {
        return _userBySessionKeyMap.get(sessionKey).getLogin();
    }

    public void logout(String sessionKey) {
        _userBySessionKeyMap.remove(sessionKey);
    }

    private User getUserByLogin(String searchLogin) throws SQLException {
        try (Statement statement = _connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format("select * from files.users where Login = '%s'", searchLogin));

            if (resultSet.next() && resultSet.isLast()) {
                String login = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                String email = resultSet.getString("Email");

                return new User(login, password, email);
            } else {
                return null;
            }
        }
    }

    private boolean containsUser(User user) throws SQLException {
        User existedUser = getUserByLogin(user.getLogin());
        return existedUser != null && existedUser.equals(user);
    }
}
