package server.httpHandlers;

import java.io.PrintWriter;

public abstract class Handler {
	//template method
	public final void handleRequest(PrintWriter out, String fileRequested) {
		// read file
		
		out = this.addHeders(out);
	}

	abstract PrintWriter addHeders(PrintWriter out);
}
