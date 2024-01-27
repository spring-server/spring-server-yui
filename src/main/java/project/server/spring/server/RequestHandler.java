package project.server.spring.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// import project.server.spring.framework.context.BeanContainer;
import project.server.spring.framework.context.BeanRegistry;
import project.server.spring.framework.servlet.DispatcherServlet;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.server.http.HttpRequest;
import project.server.spring.server.http.HttpResponse;
import project.server.spring.server.http.HttpStatus;

public final class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final BeanRegistry beanRegistry;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, BeanRegistry beanRegistry) {
        this.connection = connectionSocket;
        this.beanRegistry = beanRegistry;
    }

    public void run() {
        log.info("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (
                InputStream in = connection.getInputStream();
                OutputStream out = connection.getOutputStream()
        ) {
            HttpRequest request;
            //TODO: 리팩토링 필요
            try {
                request = new HttpRequest(in);
            } catch (Exception e) {
                // e.printStackTrace();
                log.info("invalid request: {}", e.getMessage());
                return;
            }
            HttpResponse response = new HttpResponse(out);
            log.info("content type, {}",request.getContentType());
            DispatcherServlet dispatcherServlet = new DispatcherServlet(beanRegistry);
            dispatcherServlet.service(new HttpServletRequest(request), new HttpServletResponse(response));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
