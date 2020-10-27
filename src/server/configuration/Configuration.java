package server.configuration;

import server.errors.InvalidConfigurationException;
import server.validators.PathValidator;
import server.validators.PortValidator;

public class Configuration {
	private String rootDirectory;
	private String maintenancePage;
	private int port;

	public Configuration(String rootDirectory, String maintenancePage, int port) throws InvalidConfigurationException {
		if (rootDirectory == null || !isValidRoute(rootDirectory))
			throw new InvalidConfigurationException(rootDirectory + " does not exists");
		if (maintenancePage == null || !isValidRoute(maintenancePage))
			throw new InvalidConfigurationException(maintenancePage + " does not exists");
		if (!isValidPort(port))
			throw new InvalidConfigurationException(port + " is not in range");

		this.rootDirectory = rootDirectory;
		this.maintenancePage = maintenancePage;
		this.port = port;
	}

	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) throws InvalidConfigurationException {
		if (!isValidRoute(rootDirectory))
			throw new InvalidConfigurationException(rootDirectory + "does not exists");
		this.rootDirectory = rootDirectory;
	}

	public String getMaintenancePage() {
		return maintenancePage;
	}

	public void setMaintenancePage(String maintenancePage) throws InvalidConfigurationException {
		if (!isValidRoute(maintenancePage))
			throw new InvalidConfigurationException(maintenancePage + "does not exists");
		this.maintenancePage = maintenancePage;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) throws InvalidConfigurationException {
		if (!isValidPort(port))
			throw new InvalidConfigurationException(port + " is not in range");
		this.port = port;
	}

	private boolean isValidRoute(String route) {
		return PathValidator.isValidPath(route);
	}

	private boolean isValidPort(int port) {
		return PortValidator.isValidPort(port);
	}

}
