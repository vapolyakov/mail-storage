package com.mailstorage.web;

import com.mailstorage.web.jersey.MailStorageApp;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.logging.LogManager;

/**
 * @author metal
 *
 * Main class for mail storage application. Starts jetty server listening on 8080 port and accepting .eml files
 * at /upload path.
 */
public class MailStorageMain {
    public static void main(String[] args) throws Exception {
        initLogging();

        runJettyServer(createServletContextHandler());
    }

    private static void runJettyServer(ServletContextHandler servletContextHandler) throws Exception {
        Server server = new Server(8080);
        try {
            server.setHandler(servletContextHandler);
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static ServletContextHandler createServletContextHandler() {
        ServletContainer servlet = new ServletContainer(new MailStorageApp());
        ServletHolder servletHolder = new ServletHolder(servlet);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(servletHolder, "/*");

        servletContextHandler.addEventListener(new ContextLoaderListener());
        servletContextHandler.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        servletContextHandler.setInitParameter("contextConfigLocation", MailStorageConfiguration.class.getName());
        return servletContextHandler;
    }

    private static void initLogging() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }
}
