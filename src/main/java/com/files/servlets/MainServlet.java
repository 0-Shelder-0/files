package com.files.servlets;

import com.files.services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    private final AuthorizationService _authorizationService;

    public MainServlet(AuthorizationService authorizationService) {
        _authorizationService = authorizationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "templates/index.jsp";
        String sessionKey = req.getSession().getId();

        if (_authorizationService.isLogin(sessionKey)) {
            path = "templates/files.jsp";
        }

        req.getRequestDispatcher(path).forward(req, resp);
    }
}
