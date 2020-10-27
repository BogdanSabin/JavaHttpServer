package server.httpHandlers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public abstract class Handler {
	// template method
	public final void handleRequest(PrintWriter out, BufferedOutputStream dataOut, String fileRequested,
			int fileLength) throws IOException {
		String content = getContentType(fileRequested);
		out = this.addHeders(out);
		out.println("Server: Java HTTP Server : 1.0");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println(); // blank line between headers and content, very important !
		out.flush(); // flush character output stream buffer
		
		byte[] fileData = readFileData(new File(fileRequested), fileLength);
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
	}

	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html"))
			return "text/html";
		else
			return "text/plain";
	}

	private byte[] readFileData(File file, int fileLength) throws IOException {
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

	abstract PrintWriter addHeders(PrintWriter out);
}
