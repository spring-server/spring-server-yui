package project.server.spring.framework.util;

public enum FileFormat {
	JPEG("image", "jpg"),
	PNG("image", "png"),
	CSS("stylesheet", "css"),
	JS("script", "js");

	private final String type;
	private final String extension;

	FileFormat(String type, String extension) {
		this.type = type;
		this.extension = extension;
	}

	public String getType() {
		return type;
	}

	public String getExtension() {
		return extension;
	}

	public static FileFormat ofExtension(String extension) {
		for (FileFormat format : FileFormat.values()) {
			if (format.extension.equals(extension)) {
				return format;
			}
		}
		throw new IllegalArgumentException("invalid file extension");
	}
}
