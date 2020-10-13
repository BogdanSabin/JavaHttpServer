package server.error;

@SuppressWarnings("serial")
public class InvalidConfigurationException extends Exception {
	public InvalidConfigurationException(String message) {
		super(message);
	}
}
