package project.server.spring.framework.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

	@Test
	@DisplayName("Query Param 테스트")
	void testQueryMultipleParams() {
		String startLineString = "GET /users?id=130&name=euijin HTTP/1.1";
		String expectedURI = "/users";
		String expectedIdValue = "130";
		String expectedNameValue = "euijin";
		RequestLine requestLine = testRequestLine(startLineString, expectedURI);
		Assertions.assertEquals(expectedNameValue, requestLine.getQueryParms().get("name"));
		Assertions.assertEquals(expectedIdValue, requestLine.getQueryParms().get("id"));
	}

	@Test
	@DisplayName("Query Param 테스트")
	void testQueryOneParam() {
		String startLineString = "GET /users?id=130 HTTP/1.1";
		String expectedURI = "/users";
		String expectedIdValue = "130";
		RequestLine requestLine = testRequestLine(startLineString, expectedURI);
		Assertions.assertEquals(expectedIdValue, requestLine.getQueryParms().get("id"));
	}

	private static RequestLine testRequestLine(String startLineString, String expectedURI) {
		RequestLine requestLine = new RequestLine(startLineString);
		Assertions.assertEquals(expectedURI, requestLine.getPath());
		return requestLine;
	}

	@Test
	@DisplayName("root uri 테스트")
	void testRoot() {
		String startLineString = "GET / HTTP/1.1";
		String expectedURI = "/";
		RequestLine requestLine = testRequestLine(startLineString, expectedURI);
	}
}
