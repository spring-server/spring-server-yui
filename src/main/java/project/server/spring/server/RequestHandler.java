package project.server.spring.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.servlet.DispatcherServlet;
import project.server.spring.framework.servlet.HttpServletRequest;
import project.server.spring.framework.servlet.HttpServletResponse;
import project.server.spring.server.http.HttpRequest;
import project.server.spring.server.http.HttpResponse;

public final class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private final DispatcherServlet dispatcherServlet;

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.dispatcherServlet = DispatcherServlet.getInstance();
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
			dispatcherServlet.service(new HttpServletRequest(request), new HttpServletResponse(response));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
