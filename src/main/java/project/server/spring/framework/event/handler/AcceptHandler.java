package project.server.spring.framework.event.handler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptHandler implements Handler {
	private static final Logger log = LoggerFactory.getLogger(AcceptHandler.class);
	final Selector selector;
	final ServerSocketChannel serverSocketChannel;

	AcceptHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
		this.selector = selector;
		this.serverSocketChannel = serverSocketChannel;
	}

	@Override
	public void handle() {
		try {
			final SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				log.info("client socket: {}:{}", socketChannel.getLocalAddress(), socketChannel.socket().getPort());
				log.info("server socket: {}:{}", serverSocketChannel.getLocalAddress(),
					socketChannel.socket().getPort());
				new EchoHandler(selector, socketChannel);
			}
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}
}
