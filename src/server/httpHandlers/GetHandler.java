package server.httpHandlers;

import java.io.PrintWriter;

public class GetHandler extends Handler {

	public PrintWriter addHeders(PrintWriter out) {
		out.println("HTTP/1.1 200 OK");
		return out;
	}
}
