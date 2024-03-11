package project.server.spring.framework.event;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import project.server.spring.framework.event.impl.AsyncRequestHandler;

public class EchoServer {
	private static final int BUFFER_SIZE = 10;
	private static String temp = "";

	private static final Map<SocketChannel, AsyncRequestHandler> handlers = new ConcurrentHashMap<>();

	private static final String POISON_PILL = "POISON_PILL";

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress("localhost", 5454));
		serverSocket.configureBlocking(false);
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

		while (true) {
			selector.select();
			System.out.println("check");
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				if (key.isAcceptable()) {
					register(selector, serverSocket);
				}
				if (key.isReadable()) {
					SocketChannel channel = (SocketChannel)key.channel();
					if (handlers.get(channel).read() == -1) {
						channel.register(selector, SelectionKey.OP_WRITE);
					}
				}
				if (key.isWritable()) {
					SocketChannel channel = (SocketChannel)key.channel();
					handlers.get(channel).write();
				}
				iter.remove();
			}
		}
	}

	private static void answerWithEcho(SelectionKey key, Selector selector)
		throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		SocketChannel client = (SocketChannel)key.channel();
		int size = client.read(buffer);
		System.out.println(size);
		if (size == -1 || new String(buffer.array()).trim().equals(POISON_PILL)) {
			client.register(selector, SelectionKey.OP_WRITE);
		} else {
			buffer.flip();
			byte[] dst = new byte[size];
			buffer.get(dst);
			temp += (new String(dst)).toUpperCase();
			// client.write(buffer);
			buffer.clear();
		}
	}

	//TODO: 이건 일단 놔두기
	private static void register(Selector selector, ServerSocketChannel serverSocket)
		throws IOException {
		SocketChannel client = serverSocket.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
		AsyncRequestHandler asyncRequestHandler = new AsyncRequestHandler(client);
		handlers.put(client, asyncRequestHandler);
	}

	public static Process start() throws IOException, InterruptedException {
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = EchoServer.class.getCanonicalName();

		ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

		return builder.start();
	}
}
