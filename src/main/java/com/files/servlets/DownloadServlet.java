package com.files.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

@WebServlet(name = "download", urlPatterns = "/download")
public class DownloadServlet extends HttpServlet {
    private static final int BufferSize = 1048;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
