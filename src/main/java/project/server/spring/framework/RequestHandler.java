package project.server.spring.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.http.SyncHttpRequest;
import project.server.spring.framework.http.SyncHttpResponse;
import project.server.spring.framework.servlet.DispatcherServlet;
import project.server.spring.framework.util.PageProcessor;

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
			//TODO: 리팩토링 필요
			try {
				request = new SyncHttpRequest(in);
				response = new SyncHttpResponse(out);
				log.info(request.getRequestURI());
				try {
					dispatcherServlet.service(request, response);
				} catch (Exception e) {
					SyncHttpResponse errorResponse = new SyncHttpResponse(out);
					sendError(errorResponse);
				}
			} catch (Exception e) {
				log.info("invalid request: {}", e.getMessage());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void sendError(SyncHttpResponse response) throws IOException {
		PageProcessor pageProcessor = new PageProcessor();
		String page = pageProcessor.read("400.html");
		response.setContentType("text/html; charset=UTF-8");
		response.sendError(400, page.getBytes(StandardCharsets.UTF_8));
	}
}
