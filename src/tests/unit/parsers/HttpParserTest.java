package tests.unit.parsers;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Map;

import org.junit.Test;

import server.MyServer;
import server.errors.InvalidRequestException;
import server.parsers.HttpParser;

public class HttpParserTest {

	@Test
	public void testHttpParserWithGetRequest() {
		try {
			Map<String, String> response = this.parseRequest(HttpRequestProvider.GET_REQUEST);
			assertTrue("Should be equal", response.get("method").equals("GET"));
			assertTrue("Should be equal", response.get("fileRequested").equals(HttpRequestProvider.file));
		} catch (InvalidRequestException e) {
			fail("Should not fail with this request");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidRequestException.class)
	public void testHttpParserWithInvalidRequest() throws InvalidRequestException {
		Map<String, String> response = this.parseRequest(HttpRequestProvider.INVALID_REQUEST);
		assertNull("should be null", response);
	}

	@Test
	public void testHttpParserWithHeadRequest() {
		try {
			Map<String, String> response = this.parseRequest(HttpRequestProvider.HEAD_REQUEST);
			assertTrue("Should be equal", response.get("method").equals("HEAD"));
			assertTrue("Should be equal", response.get("fileRequested").equals(HttpRequestProvider.file));
		} catch (InvalidRequestException e) {
			fail("Should not fail with this request");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidRequestException.class)
	public void testHttpParserWithNullRequest() throws InvalidRequestException {
		Map<String, String> response = this.parseRequest(null);
		assertNull("should be null", response);
	}

	@Test(expected = InvalidRequestException.class)
	public void testHttpParserWithUnknownMethodRequest() throws InvalidRequestException {
		Map<String, String> response = this.parseRequest(HttpRequestProvider.UNKNOWN_REQUEST);
		assertNull("should be null", response);
	}

	private Map<String, String> parseRequest(String request) throws InvalidRequestException {
		BufferedReader reader;
		if (request == null)
			reader = null;
		else
			reader = new BufferedReader(new StringReader(request));
		return HttpParser.parseRequest(reader, MyServer.getSupportedMethods());
	}

}
