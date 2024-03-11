package project.server.spring.framework.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadExample {
	public static void main(String[] args) {
		// Specify the file path
		String filePath = "/Users/kmula/yui-spring-new/src/main/java/project/server/spring/framework/event/sample.txt";

		// Open a FileChannel to read data from the file
		try (FileInputStream fis = new FileInputStream(filePath);
			 FileChannel fileChannel = fis.getChannel()) {

			// Allocate a ByteBuffer with a fixed capacity
			ByteBuffer buffer = ByteBuffer.allocate(10);

			// Read data from the channel into the buffer
			while (fileChannel.read(buffer) > 0) {
				// Flip the buffer to prepare it for reading
				buffer.flip();

				// Read bytes from the buffer and process them
				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					System.out.print("*");
					// Process the byte (e.g., print it)
					System.out.print((char)b);
				}

				// Clear the buffer to make it ready for writing again
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
