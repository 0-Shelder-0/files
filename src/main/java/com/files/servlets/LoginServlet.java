package com.files.servlets;

import com.files.models.UserModel;
import com.files.services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private final AuthorizationService _authorizationService;

    public LoginServlet(AuthorizationService authorizationService) {
        _authorizationService = authorizationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("templates/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionKey = req.getSession().getId();
        Map<String, String[]> parameters = req.getParameterMap();

        if (!parameters.containsKey("login") || !parameters.containsKey("password")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            String login = parameters.get("login")[0];
            String password = parameters.get("password")[0];

            if (login.length() == 0 || password.length() == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login and password can't be empty");
            } else {
                UserModel user = new UserModel(login, password);
                try {
                    _authorizationService.login(sessionKey, user);
                    resp.sendRedirect("/files");
                } catch (Exception exception) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
                }
            }
        }
    }
}
