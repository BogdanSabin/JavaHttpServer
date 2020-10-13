package server.error;

@SuppressWarnings("serial")
public class InvalidRouteException extends Exception {
	public InvalidRouteException(String message) {
		super(message);
	}
}
