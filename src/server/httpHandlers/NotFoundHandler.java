package server.httpHandlers;

public class NotFoundHandler extends Handler {

	public String addHeders() {
		return "HTTP/1.1 404 Not Fount\n";
	}
}
