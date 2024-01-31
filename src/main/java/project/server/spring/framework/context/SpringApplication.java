package project.server.spring.framework.context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.server.RequestHandler;

public class SpringApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringApplication.class);

	private static final int DEFAULT_PORT = 8083;

	static {
		ApplicationContext context = null;
		try {
			context = new ApplicationContext("project");
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void run(String[] args) throws IOException {
		log.info("java version : {}", System.getProperty("java.version"));
		int port = getPort(args);
		try (ServerSocket listenSocket = new ServerSocket(port)) {
			log.info("Web Application Server started {} port.", port);
			Socket connection;
			while ((connection = listenSocket.accept()) != null) {
				RequestHandler requestHandler = new RequestHandler(connection);
				requestHandler.start();
			}
		}
	}

	private static int getPort(String[] args) {
		int port = 0;
		if (args == null || args.length == 0) {
			port = DEFAULT_PORT;
		} else {
			port = Integer.parseInt(args[0]);
		}
		return port;
	}

}
