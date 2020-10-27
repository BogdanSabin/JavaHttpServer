package server.errors;

@SuppressWarnings("serial")
public class InvalidRequestException extends Exception {
	public InvalidRequestException(String message) {
		super(message);
	}
}
