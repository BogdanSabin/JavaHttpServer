package server.httpHandlers;

import java.io.PrintWriter;

public class NotFoundHandler extends Handler {

	public PrintWriter addHeders(PrintWriter out) {
		out.println("HTTP/1.1 404 Not Fount");
		return out;
	}
}
