package project.server.spring.framework.http;

public class HttpRequest {
	// private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
	//
	// private HttpMethod httpMethod;
	// private String path;
	//
	// private MediaType contentType;
	//
	// private char[] body;
	//
	// private HttpHeaders headers;
	// private QueryParams queryParams;
	//
	// public HttpRequest(InputStream is) {
	// 	try {
	// 		BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
	// 		RequestLine requestLine = createRequestLine(br);
	// 		log.info("{}", requestLine);
	// 		this.httpMethod = requestLine.getHttpMethod();
	// 		this.path = requestLine.getPath();
	// 		this.queryParams = requestLine.getQueryParms();
	// 		this.headers = createHeaders(br);
	// 		this.contentType = headers.getContentType();
	// 		if (headers.getContentLength() > 0) {
	// 			this.body = IOUtils.readData(br, headers.getContentLength());
	// 			log.info("body :{}", body);
	// 		}
	// 	} catch (IOException e) {
	// 		log.error(e.getMessage());
	// 	}
	// }
	//
	// private RequestLine createRequestLine(BufferedReader br) throws IOException {
	// 	String line = br.readLine();
	// 	log.info("data : {}", line);
	// 	if (line == null) {
	// 		throw new IllegalStateException("invalid request format");
	// 	}
	// 	return new RequestLine(line);
	// }
	//
	// private HttpHeaders createHeaders(BufferedReader br) throws IOException {
	// 	HttpHeaders headers = new HttpHeaders();
	// 	String line;
	// 	while (!"".equals(line = br.readLine())) {
	// 		headers.parse(line);
	// 	}
	// 	return headers;
	// }
	//
	// public MediaType getContentType() {
	// 	return headers.getContentType();
	// }
	//
	// public HttpMethod getHttpMethod() {
	// 	return httpMethod;
	// }
	//
	// public String getPath() {
	// 	return path;
	// }
	//
	// public char[] getBody() {
	// 	return body;
	// }
	//
	// public HttpHeaders getHeaders() {
	// 	return headers;
	// }
	//
	// public QueryParams getQueryParams() {
	// 	return queryParams;
	// }
	//
	// public Cookies getCookies() {
	// 	return headers.getCookies();
	// }
}
