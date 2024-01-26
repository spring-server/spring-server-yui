package project.server.spring.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.context.BeanRegistry;
import project.server.spring.framework.context.ComponentRegistrator;
import project.server.spring.framework.context.ComponentRegistratorImpl;
import project.server.spring.framework.context.JavaComponentRegistrator;

public class WebServer {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8083;

    public static void main(String[] args) throws Exception {
        log.info("java version : {}", System.getProperty("java.version"));
        BeanRegistry beanContainer = new BeanRegistry();
        ComponentRegistrator componentRegistrator = new JavaComponentRegistrator(beanContainer);
        componentRegistrator.start();
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection, beanContainer);
                requestHandler.start();
            }
        }
    }
}
