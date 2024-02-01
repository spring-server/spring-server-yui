package project.server.spring.server.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

	@Test
	@DisplayName("Query Param 테스트")
	void testQueryParam() {
		String startLineString = "GET /users?id=130&name=euijin HTTP/1.1";
		String expectedURI = "/users";
		String expectedIdValue = "130";
		String expectedNameValue = "euijin";
		RequestLine requestLine = new RequestLine(startLineString);
		Assertions.assertEquals(expectedURI, requestLine.getPath());
		QueryParams queryParms = requestLine.getQueryParms();
		String s = queryParms.get("name");
		Assertions.assertEquals(queryParms.get("name"), expectedNameValue);
		Assertions.assertEquals(queryParms.get("id"), expectedIdValue);
	}
}
