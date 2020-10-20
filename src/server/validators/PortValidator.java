package server.validators;

public class PortValidator {
	private static final int MIN_PORT_NUMBER = 1025;
	private static final int MAX_PORT_NUMBER = 65535;
	
	public static boolean isValidPort(int port) {
		return port >= MIN_PORT_NUMBER && port <= MAX_PORT_NUMBER;
	}
}
