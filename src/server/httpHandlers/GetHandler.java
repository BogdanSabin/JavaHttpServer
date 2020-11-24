package server.httpHandlers;

public class GetHandler extends Handler {

	public String addHeders() {
		return "HTTP/1.1 200 OK\n";
	}
}
