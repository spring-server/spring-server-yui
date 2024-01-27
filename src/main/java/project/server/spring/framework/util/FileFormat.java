package project.server.spring.framework.util;

public enum FileFormat {
	JPEG("image", "jpg"),
	PNG("image", "png"),
	CSS("stylesheet", "css"),
	JS("script","js");

	private final String type;
	private final String subType;

	FileFormat(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	public static FileFormat ofExtension(String extension) {
		for (FileFormat format : FileFormat.values()) {
			if (format.subType.equals(extension)) {
				return format;
			}
		}
		throw new IllegalArgumentException("invalid file extension");
	}
}
