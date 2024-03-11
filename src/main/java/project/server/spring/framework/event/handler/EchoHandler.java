package project.server.spring.framework.event.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import project.server.spring.framework.event.http.AsyncHttpRequest;
import project.server.spring.framework.message.Parser;

public class EchoHandler implements Handler {
	private static final int BUFFER_CAPPACITY = 10241024;
	private static final Logger log = LoggerFactory.getLogger(EchoHandler.class);
	static final int READING = 0, SENDING = 1;
	final SocketChannel socketChannel;
	final SelectionKey selectionKey;
	final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_CAPPACITY);
	final byte[] byteArray = new byte[BUFFER_CAPPACITY];
	int state = READING;
	private AsyncHttpRequest httpRequest;

	EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
		log.info("[INFO] Accept Event occurs ");
		this.socketChannel = socketChannel;
		this.socketChannel.configureBlocking(false);
		// Attach a handler to handle when an event occurs in SocketChannel.
		selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
		selectionKey.attach(this);
		selector.wakeup();
	}

	@Override
	public void handle() {
		try {
			if (state == READING) {
				read();
			} else if (state == SENDING) {
				send();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	void read() throws IOException {
		int readCount = socketChannel.read(buffer);
		log.info("{}", readCount);
		if (readCount < 0) {
			buffer.clear();
			socketChannel.close();
			return;
		}
		buffer.flip();
		buffer.get(byteArray, 0, readCount);
		String receivedString = new String(byteArray, 0, readCount);
		System.out.println(receivedString);
		httpRequest = Parser.read(socketChannel, receivedString);
		log.info("{}", httpRequest);
		if (httpRequest != null) {
			selectionKey.interestOps(SelectionKey.OP_WRITE);
			selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_READ);
			state = SENDING;
		}
	}

	void send() throws IOException {
		buffer.clear();
		buffer.put("hello".getBytes());
		buffer.flip();
		socketChannel.write(buffer);
		buffer.clear();
		selectionKey.interestOps(SelectionKey.OP_READ);
		selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
		// selectionKey.cancel();
		//TODO: 소켓 연결 취소 고치기
		socketChannel.close();
		state = READING;
	}
}
