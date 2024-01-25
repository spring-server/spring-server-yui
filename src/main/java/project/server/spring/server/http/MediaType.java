package project.server.spring.server.http;

public enum MediaType {
	ALL("*", "*"),
	APPLICATION_JSON("application", "json"),
	APPLICATION_PDF ("application", "pdf"),
	APPLICATION_FORM_URLENCODED("application", "x-www-form-urlencoded"),
	IMAGE_GIF("image", "gif"),
	IMAGE_JPEG("image", "jpeg"),
	IMAGE_PNG("image", "png"),
	MULTIPART_FORM_DATA_VALUE("multipart","form-data"),
	TEXT_HTML("text", "html"),
	TEXT_PLAIN("text", "plain");

	private static final String FORWARD_SLASH = "/";//TODO: parsing 관련 리팩토링

	private String type;
	private String subType;
	private MediaType(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	public static MediaType ofValue(String value) {
		String[] cmds = value.split(FORWARD_SLASH);
		if (cmds.length != 2) {
			throw new IllegalArgumentException("invald media type value");
		}
		for (MediaType mediaType : MediaType.values()) {
			if (mediaType.type.equals(cmds[0]) && mediaType.subType.equals(cmds[1])) {
				return mediaType;
			}
		}
		throw new IllegalArgumentException("invalid media type value");
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}
}
