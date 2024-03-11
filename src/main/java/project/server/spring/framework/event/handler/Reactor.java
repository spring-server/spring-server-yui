package project.server.spring.framework.event.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class Reactor implements Runnable {
	final Selector selector;
	final ServerSocketChannel serverSocketChannel;

	Reactor(int port) throws IOException {
		selector = Selector.open();

		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// Attach a handler to handle when an event occurs in ServerSocketChannel.
		selectionKey.attach(new AcceptHandler(selector, serverSocketChannel));
	}

	public void run() {
		try {
			while (true) {
				selector.select();
				Set<SelectionKey> selected = selector.selectedKeys();
				for (SelectionKey selectionKey : selected) {
					dispatch(selectionKey);
				}
				selected.clear();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	void dispatch(SelectionKey selectionKey) {
		Handler handler = (Handler)selectionKey.attachment();
		handler.handle();
	}

	void serverFunction(SelectionKey selectionKey) {
		Handler handler = (Handler)selectionKey.attachment();
		handler.handle();
	}
}
