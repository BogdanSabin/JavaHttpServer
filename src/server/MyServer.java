package server;

import java.io.IOException;
import java.net.Socket;

import server.configuration.Configuration;
import server.error.InvalidConfigurationException;

public class MyServer extends Thread{
	protected Socket clientSocket;
	private static ServerState state = ServerState.STOPPED; //default state
	private static Configuration config;
	
	public MyServer(Socket socket, Configuration config) throws InvalidConfigurationException {
		if(config == null || socket == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		this.clientSocket = socket;
		MyServer.config = config;
		MyServer.state = ServerState.RUNNING;
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
	
	public static ServerState getServerState() {
		return MyServer.state;
	}
	
	public static void setState(ServerState state) {
		MyServer.state = state;
	}
	
	public static Configuration getConfig() {
		return MyServer.config;
	}
	
	public static void  setConfig(Configuration config) throws InvalidConfigurationException {
		if(config == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		MyServer.config = config;
	}
				
	
}
