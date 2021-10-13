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

    private final AuthorizationService _authorizationService;

    public FilesServlet(AuthorizationService authorizationService) {
        _authorizationService = authorizationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionKey = req.getSession().getId();
        String templatePath = "templates/index.jsp";

        if (_authorizationService.isLogin(sessionKey)) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            String strDate = dateFormat.format(new Date());
            req.setAttribute("appTime", strDate);

            Object pathAttribute = req.getParameter("path");
            String directoryPath = pathAttribute != null ? pathAttribute.toString() : "/";
            req.setAttribute("path", directoryPath);

            List<FileModel> files = getFiles(directoryPath);
            req.setAttribute("files", files);

            templatePath = "templates/files.jsp";
        }

        req.getRequestDispatcher(templatePath).forward(req, resp);
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
