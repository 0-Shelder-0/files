package com.files.main;

import com.files.dbService.DBService;
import com.files.services.AuthorizationService;
import com.files.servlets.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Application implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        String baseDirectory = "D:/Home/";

        DBService dbService = new DBService();
        AuthorizationService authorizationService = new AuthorizationService(dbService);

        context.addServlet("main", new MainServlet()).addMapping("/main");
        context.addServlet("login", new LoginServlet(authorizationService)).addMapping("/login");
        context.addServlet("register", new RegisterServlet(authorizationService)).addMapping("/register");
        context.addServlet("logout", new LogoutServlet(authorizationService)).addMapping("/logout");
        context.addServlet("files", new FilesServlet(authorizationService, baseDirectory)).addMapping("/files");
        context.addServlet("download", new DownloadServlet(authorizationService, baseDirectory)).addMapping("/download");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
