package com.files.models;

import java.util.Objects;

public class User {
    private final String _login;
    private final String _password;

    public User(String login, String password) {
        _login = login;
        _password = password;
    }

    public String getLogin() {
        return _login;
    }

    public String getPassword() {
        return _password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User)obj;
        return Objects.equals(user.getLogin(), _login) && Objects.equals(user.getPassword(), _password);
    }

    @Override
    public int hashCode() {
        return 73 * getLogin().hashCode() + 29 * getPassword().hashCode();
    }
}
