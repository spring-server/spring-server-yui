package project.server.spring.framework.http;

import java.util.Arrays;
import java.util.function.Predicate;

public enum FileExtension {
	CSS("css", "text/css"),
	JAVA_SCRIPT("js", "application/javascript"),
	IMAGE_X_ICON("ico", "image/x-icon"),
	DEFAULT("*", "text/html;charset=utf-8");

	private final String type;
	private final String value;

	FileExtension(
		String type,
		String value
	) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public static FileExtension findByType(String type) {
		if (type == null || type.isBlank()) {
			throw new IllegalArgumentException("no file extension");
		}
		return Arrays.stream(values())
			.filter(equals(type))
			.findAny()
			.orElseGet(() -> FileExtension.DEFAULT);
	}

	private static Predicate<FileExtension> equals(String type) {
		return fileExtension -> fileExtension.type.equals(type);
	}
}
