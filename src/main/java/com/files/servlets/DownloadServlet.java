package com.files.servlets;

import com.files.services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public class DownloadServlet extends HttpServlet {

    private static final int BufferSize = 4096;
    private final AuthorizationService _authorizationService;

    public DownloadServlet(AuthorizationService authorizationService) {
        _authorizationService = authorizationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String sessionKey = req.getSession().getId();
        if (!_authorizationService.isLogin(sessionKey)) {
            throw new ServletException("User not authorized");
        }

        Object pathAttribute = req.getParameter("path");
        String path = pathAttribute != null ? pathAttribute.toString() : "/";
        String fileName = Path.of(path).getFileName().toString();

        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));

        try (InputStream inputStream = new FileInputStream(path);
             OutputStream outputStream = resp.getOutputStream()) {

            byte[] buffer = new byte[BufferSize];

            int numBytesRead;
            while ((numBytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, numBytesRead);
            }
        }
    }
}
