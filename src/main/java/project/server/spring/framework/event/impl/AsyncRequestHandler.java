package project.server.spring.framework.event.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class AsyncRequestHandler {
	private final SocketChannel channel;
	private String message = "";
	private static final int BUFFER_SIZE = 10;
	private final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

	public AsyncRequestHandler(SocketChannel channel) {
		this.channel = channel;
	}

	public int read() throws IOException {
		int size = channel.read(buffer);
		if (size == -1) {
			System.out.println("once");
			buffer.clear();
			return size;
		}
		buffer.flip();
		byte[] dst = new byte[size];
		buffer.get(dst);
		message += (new String(dst)).toUpperCase();
		buffer.clear();
		return size;
	}

	//TODO: write 수정 필요
	public int write() throws IOException {
		int size = Math.min(BUFFER_SIZE, message.length());
		ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes(), 0, size);
		System.out.println(message);
		message = message.substring(size);
		writeBuffer.clear();
		int ret = channel.write(writeBuffer);
		System.out.println(ret);
		if (ret < 0) {
			channel.close();
			return -1;
		}
		return size;
	}

	public int readFile() {
		return 0;
	}
}
