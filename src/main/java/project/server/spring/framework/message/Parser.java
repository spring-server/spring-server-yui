package project.server.spring.framework.message;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import project.server.spring.framework.event.http.AsyncHttpRequest;
import project.server.spring.framework.event.http.RequestHttpMessage;

public class Parser {
	private static final Map<SocketChannel, RequestHttpMessage> httpMessageMap = new ConcurrentHashMap<>();
	private static final String CRLF = "\r\n";

	public static AsyncHttpRequest read(SocketChannel socketChannel, String message) {
		RequestHttpMessage httpMessage = httpMessageMap.getOrDefault(socketChannel, new RequestHttpMessage());
		//httpMessage.parse(message);
		httpMessage.execute(message);
		if (httpMessage.isCompleted()) {
			return new AsyncHttpRequest(httpMessage.getHttpHeaders(), httpMessage.getRequestLine(),
				httpMessage.getHttpBody());
		}
		return null;
	}
}
