package server.httpHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import server.errors.InvalidParameterException;

public abstract class Handler {
	// template method
	public final String handleRequest(String fileRequested, int fileLength) {
		String content = getContentType(fileRequested);
		StringBuilder headers = new StringBuilder();

		headers.append(this.addHeders());
		headers.append("Server: Java HTTP Server : 1.0\n");
		headers.append("Date: " + new Date() + "\n");
		headers.append("Content-type: " + content + "\n");
		headers.append("Content-length: " + fileLength + "\n");
		headers.append("\n"); // blank line between headers and content, very important !

		return headers.toString();
	}

	public static final byte[] getFileBytes(File file, int fileLength) throws IOException, InvalidParameterException {
		if (file == null)
			throw new InvalidParameterException("File must not be null");
		if (!file.exists())
			throw new InvalidParameterException("File must exists null");
		if (fileLength <= 0)
			throw new InvalidParameterException("lenght mush be positive");

		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];

		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null)
				fileIn.close();
		}

		return fileData;
	}

	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}

	abstract String addHeders();
}
