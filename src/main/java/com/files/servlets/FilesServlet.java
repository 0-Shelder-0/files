package com.files.servlets;

import com.files.entities.FileModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet(name = "files", urlPatterns = "/files")
public class FilesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        String strDate = dateFormat.format(new Date());
        req.setAttribute("appTime", strDate);

        Object pathAttribute = req.getParameter("path");
        String path = pathAttribute != null ? pathAttribute.toString() : "/";
        req.setAttribute("path", path);

        List<FileModel> files = getFiles(path);
        req.setAttribute("files", files);

        req.getRequestDispatcher("files.jsp").forward(req, resp);
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
