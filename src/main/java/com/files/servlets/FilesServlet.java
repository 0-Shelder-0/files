package com.files.servlets;

import com.files.models.FileModel;
import com.files.services.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilesServlet extends HttpServlet {

    private final String BaseDirectory;
    private final AuthorizationService _authorizationService;

    public FilesServlet(AuthorizationService authorizationService, String baseDirectory) {
        _authorizationService = authorizationService;
        BaseDirectory = baseDirectory;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionKey = req.getSession().getId();

        if (_authorizationService.isLogin(sessionKey)) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            String strDate = dateFormat.format(new Date());
            req.setAttribute("appTime", strDate);

            Object pathAttribute = req.getParameter("path");
            String directoryPath = pathAttribute != null ? pathAttribute.toString() : "/";
            String login = _authorizationService.getLogin(sessionKey);
            directoryPath = BaseDirectory + login + directoryPath;
            req.setAttribute("path", directoryPath);

            createDirectoryIfNotExist(directoryPath);
            List<FileModel> files = getFiles(directoryPath);
            req.setAttribute("files", files);

            req.getRequestDispatcher("templates/files.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized");
        }
    }

    private void createDirectoryIfNotExist(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private List<FileModel> getFiles(String path) {
        File folder = new File(path);
        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                     .map(file -> {
                         try {
                             return new FileModel(file);
                         } catch (IOException e) {
                             return null;
                         }
                     })
                     .filter(Objects::nonNull)
                     .collect(Collectors.toList());
    }
}
