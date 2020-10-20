package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.configuration.Configuration;
import server.error.InvalidConfigurationException;

public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.print("Usage java Main rootDirectoryPath maintenancePagePath portNumber");
			System.exit(-1);
		} else {
			ServerSocket serverSocket = null;
			String rootDirectoryPath = args[0];
			String maintenancePagePath = args[1];
			int port = Integer.parseInt(args[2]);
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("Server started...");
				Configuration config = new Configuration(rootDirectoryPath, maintenancePagePath, port);
				while (true) {
					System.out.println("Waiting for connections...");
					new MyServer(serverSocket.accept(), config);
				}

			} catch (IOException | InvalidConfigurationException e) {
				System.out.println("Error: " + e);
			} finally {
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.out.println("Error " + e);
					System.exit(-1);
				}
			}
		}
	}
}
