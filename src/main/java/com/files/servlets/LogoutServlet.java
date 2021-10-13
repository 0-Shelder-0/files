package com.files.servlets;

import com.files.services.AuthorizationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private final AuthorizationService _authorizationService;

    public LogoutServlet(AuthorizationService authorizationService) {
        _authorizationService = authorizationService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionKey = req.getSession().getId();
        _authorizationService.logout(sessionKey);
        resp.sendRedirect("/login");
    }
}
