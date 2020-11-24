package tests.unit.httpHandlers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Test;

import server.errors.InvalidParameterException;
import server.httpHandlers.GetHandler;
import server.httpHandlers.Handler;
import server.httpHandlers.NotFoundHandler;

public class HandlerTest {
	public final static String HTML_FILE = "file.html";
	public final static String TEXT_FILE = "file.txt";
	public final static String UNKNOWN_EXTENSION_FILE = "file.ppt";
	private final static String FILE_PATH = System.getProperty("user.home") + File.separator + "Desktop"
			+ File.separator + "file.txt";

	@After
	public void tearDown() {
		File file = new File(HandlerTest.FILE_PATH);
		if (file.exists())
			file.delete();
	}

	@Test
	public void testHeaderConstructionForGetHandlerWithHtmlFile() {
		Handler handler = new GetHandler();
		int fileLength = 10;
		String headers = handler.handleRequest(HandlerTest.HTML_FILE, fileLength);
		this.checkHeaders(headers, HandlerTest.HTML_FILE, fileLength, "text/html", handler);
	}

	@Test
	public void testHeaderConstructionForGetHandlerWithTextFile() {
		Handler handler = new GetHandler();
		int fileLength = 10;
		String headers = handler.handleRequest(HandlerTest.TEXT_FILE, fileLength);
		this.checkHeaders(headers, HandlerTest.TEXT_FILE, fileLength, "text/plain", handler);
	}

	@Test
	public void testHeaderConstructionForNotFoundHandlerWithHtmlFile() {
		Handler handler = new NotFoundHandler();
		int fileLength = 10;
		String headers = handler.handleRequest(HandlerTest.HTML_FILE, fileLength);
		this.checkHeaders(headers, HandlerTest.HTML_FILE, fileLength, "text/html", handler);
	}

	@Test
	public void testHeaderConstructionForNotFoundHandlerWithTextFile() {
		Handler handler = new NotFoundHandler();
		int fileLength = 10;
		String headers = handler.handleRequest(HandlerTest.TEXT_FILE, fileLength);
		this.checkHeaders(headers, HandlerTest.TEXT_FILE, fileLength, "text/plain", handler);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFileReadWithNullFile() throws InvalidParameterException, IOException {
		Handler.getFileBytes(null, 10);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFileReadWithNonExistingFile() throws InvalidParameterException, IOException {
		Handler.getFileBytes(new File("NonExistingFile"), 10);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFileReadWithNegativeLength() throws IOException, InvalidParameterException {
		File file = new File(HandlerTest.FILE_PATH);
		file.createNewFile();
		Handler.getFileBytes(file, -10);
	}

	@Test
	public void testFileReadWithOkParameters() {
		try {
			FileWriter writer = new FileWriter(HandlerTest.FILE_PATH, true);
			String content = "Hello World";
			writer.write(content);
			writer.close();
			byte[] fileData = Handler.getFileBytes(new File(HandlerTest.FILE_PATH), content.length());
			assertTrue("should not be null", fileData != null);
			assertTrue("should be equal", content.equals(new String(fileData, StandardCharsets.UTF_8)));
		} catch (IOException | InvalidParameterException e) {
			// TODO Auto-generated catch block
			fail("Shoul not fail here");
			e.printStackTrace();
		}
	}

	private void checkHeaders(String headers, String file, int fileLength, String contentType, Handler handler) {
		String[] tokens = headers.split("\\n");
		if (handler instanceof GetHandler)
			assertTrue("should be equal", tokens[0].equals("HTTP/1.1 200 OK"));
		if (handler instanceof NotFoundHandler)
			assertTrue("should be equal", tokens[0].equals("HTTP/1.1 404 Not Fount"));
		assertTrue("should be equal", tokens[1].equals("Server: Java HTTP Server : 1.0"));
		assertTrue("should be equal", tokens[2].startsWith("Date: "));
		assertTrue("should be equal", tokens[3].equals("Content-type: " + contentType));
		assertTrue("should be equal", tokens[4].equals("Content-length: " + fileLength));
		assertTrue("should be equal", tokens.length == 5);

	}

}
