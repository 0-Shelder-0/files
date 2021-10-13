package com.files.services;

import com.files.models.User;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationService {
    private final Map<String, User> _userByLoginMap = new HashMap<>();
    private final Map<String, User> _userBySessionKeyMap = new HashMap<>();

    public void register(User user) throws Exception {
        String login = user.getLogin();
        if (!_userByLoginMap.containsKey(login)) {
            _userByLoginMap.put(login, user);
        } else {
            throw new Exception("This login already exists");
        }
    }

    public boolean isLogin(String sessionKey) {
        return _userBySessionKeyMap.containsKey(sessionKey);
    }

    public void login(String sessionKey, User user) throws Exception {
        if (!containsUser(user)) {
            throw new Exception("User not exists");
        }

        if (!_userBySessionKeyMap.containsKey(sessionKey)) {
            _userBySessionKeyMap.put(sessionKey, user);
        }
    }

    public void logout(String sessionKey) {
        _userBySessionKeyMap.remove(sessionKey);
    }

    private boolean containsUser(User user) {
        String login = user.getLogin();
        if (!_userByLoginMap.containsKey(login)) {
            return false;
        }

        User existedUser = _userByLoginMap.get(login);
        return existedUser.equals(user);
    }
}
