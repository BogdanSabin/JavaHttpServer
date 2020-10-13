package server;

import java.io.File;

import server.error.InvalidConfigurationException;

public class Configuration {
	private String rootDirectory;
	private String maintenancePage;
	private int port;
	private final int MIN_PORT_NUMBER = 1025;
	private final int MAX_PORT_NUMBER = 65535;
	
	
	public Configuration(String rootDirectory, String maintenancePage, int port) throws InvalidConfigurationException {
		if(rootDirectory == null || !isValidRoute(rootDirectory) ) throw new InvalidConfigurationException(rootDirectory + " does not exists");
		if(maintenancePage == null || !isValidRoute(maintenancePage) ) throw new InvalidConfigurationException(maintenancePage + " does not exists");
		if(!isValidPort(port)) throw new InvalidConfigurationException(port + " is not in range");
		
		this.rootDirectory = rootDirectory;
		this.maintenancePage = maintenancePage;
		this.port = port;
	}
	
	public String getRootDirectory() {
		return rootDirectory;
	}
	
	public void setRootDirectory(String rootDirectory) throws InvalidConfigurationException {
		if(!isValidRoute(rootDirectory)) throw new InvalidConfigurationException(rootDirectory + "does not exists");
		this.rootDirectory = rootDirectory;
	}
	
	public String getMaintenancePage() {
		return maintenancePage;
	}
	
	public void setMaintenancePage(String maintenancePage) throws InvalidConfigurationException {
		if(!isValidRoute(maintenancePage)) throw new InvalidConfigurationException(maintenancePage + "does not exists");
		this.maintenancePage = maintenancePage;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) throws InvalidConfigurationException {
		if(!isValidPort(port)) throw new InvalidConfigurationException(port + " is not in range");
		this.port = port;
	}
	

	private boolean isValidRoute(String route) {
		return new File(route).exists();	
	}
	
	private boolean isValidPort(int port) {
		return port >= MIN_PORT_NUMBER && port <= MAX_PORT_NUMBER;
	}
	
}
