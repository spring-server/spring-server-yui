package project.server.spring.framework.event.handler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncServer {
	private static final Logger log = LoggerFactory.getLogger(AcceptHandler.class);
	private static final int THREAD_NUMBER = 10;

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
		// Runnable runnable = executorService.execute(runnable);
		Reactor reactor = new Reactor(8080);
		reactor.run();
		// try {
		// 	reactor = new Reactor(8080);
		// 	reactor.run();
		// 	executorService.submit(reactor);
		// } catch (IOException e) {
		// 	log.error(e.getMessage());
		// }
	}
}
