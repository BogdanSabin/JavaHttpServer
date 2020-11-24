package server.errors;

@SuppressWarnings("serial")
public class InvalidParameterException extends Exception {
	public InvalidParameterException(String message) {
		super(message);
	}
}
