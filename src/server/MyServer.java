package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import server.configuration.Configuration;
import server.errors.InvalidConfigurationException;
import server.errors.InvalidRequestException;
import server.httpHandlers.GetHandler;
import server.httpHandlers.Handler;
import server.parsers.HttpParser;

public class MyServer extends Thread {
	protected Socket clientSocket;
	private static ServerState state = ServerState.STOPPED; // default state
	private static Configuration config;
	private static final String[] supportedMethods = { "GET", "HEAD" };

	public MyServer(Socket socket, Configuration config) throws InvalidConfigurationException {
		if (config == null || socket == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		this.clientSocket = socket;
		MyServer.config = config;
		MyServer.state = ServerState.RUNNING;
		this.start();
	}

	public void run() {
		System.out.println("Run method invoked");
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			out = new PrintWriter(this.clientSocket.getOutputStream());
			dataOut = new BufferedOutputStream(this.clientSocket.getOutputStream());
			if (MyServer.state == ServerState.MAINTENANCE)
				System.out.println("Maintenance");
			else {
				Map<String, String> request = HttpParser.parseRequest(in, MyServer.supportedMethods);
				
				String filePath = this.composePath(request.get("fileRequested"), false);
				int fileLenght = (int) new File(filePath).length();
				Handler responseHandler = null;

				switch (request.get("method")) {
				case "HEAD":
					responseHandler = new GetHandler();
					responseHandler.handleRequest(out, dataOut, filePath, fileLenght);
					break;
				case "GET":
					responseHandler = new GetHandler();
					responseHandler.handleRequest(out, dataOut, filePath, fileLenght);
					break;

				default:
					break;
				}
			}

		} catch (IOException | InvalidRequestException e) {
			System.out.println("Error in run method: " + e);
		} finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				this.clientSocket.close();
			} catch (IOException e) {
				System.out.println("Error at socket close " + e);
				System.exit(-1);
			}
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

	public static String[] getSupportedMethods() {
		return MyServer.supportedMethods;
	}

	public static void setConfig(Configuration config) throws InvalidConfigurationException {
		if (config == null)
			throw new InvalidConfigurationException("Server has not been configured!");
		MyServer.config = config;
	}

	private String composePath(String file, Boolean isMaintenance) {
		String basePath;
		if (isMaintenance)
			basePath = MyServer.config.getMaintenancePage();
		else
			basePath = MyServer.config.getRootDirectory();
		
		if(file.length() == 1 && file.endsWith("/"))
			file = "index.html";
		return new File(basePath, file).getPath();
	}
}
