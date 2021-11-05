package com.files.services;

import com.files.dbService.DBService;
import com.files.exceptions.DBException;
import com.files.models.UserModel;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationService {
    private final DBService _dbService;
    private final Map<String, UserModel> _userBySessionKeyMap = new HashMap<>();

    public AuthorizationService(DBService dbService) {
        _dbService = dbService;
    }

    public void register(UserModel user) throws Exception {
        UserModel existedUser = _dbService.getUserByLogin(user.getLogin());
        if (existedUser != null) {
            throw new Exception("This login already exists");
        }

        _dbService.addUser(user);
    }

    public boolean isLogin(String sessionKey) {
        return _userBySessionKeyMap.containsKey(sessionKey);
    }

    public void login(String sessionKey, UserModel user) throws DBException {
        if (!containsUser(user)) {
            throw new DBException("User not exists");
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

    private boolean containsUser(UserModel user) throws DBException {
        UserModel existedUser = _dbService.getUserByLogin(user.getLogin());
        return existedUser != null && existedUser.equals(user);
    }
}
