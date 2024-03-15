package project.server.spring.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.http.SyncHttpRequest;
import project.server.spring.framework.http.SyncHttpResponse;
import project.server.spring.framework.servlet.DispatcherServlet;

public final class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private final DispatcherServlet dispatcherServlet;

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.dispatcherServlet = DispatcherServlet.getInstance();
	}

	@Override
	public void run() {
		log.info("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
		try (
			InputStream in = connection.getInputStream();
			OutputStream out = connection.getOutputStream()
		) {
			SyncHttpRequest request;
			SyncHttpResponse response;
			try {
				request = new SyncHttpRequest(in);
				response = new SyncHttpResponse(out);
				dispatcherServlet.service(request, response);
			} catch (Exception e) {
				log.error("invalid request: {}", e.getMessage());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
