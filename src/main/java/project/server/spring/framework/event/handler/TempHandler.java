package project.server.spring.framework.event.handler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempHandler implements Handler {

	private static final Logger log = LoggerFactory.getLogger(TempHandler.class);
	final Selector selector;
	final ServerSocketChannel serverSocketChannel;

	TempHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
		this.selector = selector;
		this.serverSocketChannel = serverSocketChannel;
	}

	@Override
	public void handle() {
		try {
			final SocketChannel clientSocketChannel = serverSocketChannel.accept();
			// if (socketChannel != null) {
			// 	new EchoHandler(selector, socketChannel);
			// }
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}
}
