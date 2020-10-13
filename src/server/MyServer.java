package server;

import java.io.IOException;
import java.net.Socket;

import server.error.InvalidConfigurationException;

public class MyServer extends Thread{
	protected Socket clientSocket;
	private ServerState state = ServerState.STOPPED; //default state
	private Configuration config;
	
	public MyServer(Socket socket, Configuration config) throws InvalidConfigurationException {
		if(config == null || socket == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		this.clientSocket = socket;
		this.config = config;
		this.state = ServerState.RUNNING;
		this.start();
	}
	
	public void run() {
		System.out.println("Run method invoked");
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("Error in run method: " + e);
		}
	}
	
	public ServerState getServerState() {
		return state;
	}
	
	public void setState(ServerState state) {
		this.state = state;
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public void setConfig(Configuration config) throws InvalidConfigurationException {
		if(config == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		this.config = config;
	}
				
	
}
