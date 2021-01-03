package gui.helpers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

import server.MyServer;
import server.ServerState;
import server.configuration.Configuration;
import server.errors.InvalidConfigurationException;

@SuppressWarnings("unused")
public class ServerRunner implements Runnable {

	private Thread worker;
	private MyServer server;
	private ServerSocket serverSocket;
	private Configuration config;
	private final AtomicBoolean running;

	public ServerRunner(Configuration config) {
		this.config = config;
		try {
			serverSocket = new ServerSocket(config.getPort());
		} catch (IOException e) {
			System.out.println("Eroot server runner config: " + e);
		}
		this.running = new AtomicBoolean(true);
	}

	public void goMaintenance(boolean isMaintenance) {
		if (isMaintenance)
			MyServer.setState(ServerState.MAINTENANCE);
		else
			MyServer.setState(ServerState.RUNNING);
	}

	public void stop() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			System.out.println("Error while stopping server " + e);
		}
		this.running.set(false);
	}

	public void restart() {
		running.set(true);
		worker = new Thread(this);
		worker.start();
	}

	public void start() {
		running.set(true);
		worker = new Thread(this);
		worker.start();
	}

	public void changeConfig(Configuration config) {
		this.config = config;
		try {
			MyServer.setConfig(this.config);
		} catch (InvalidConfigurationException e) {
			System.out.println("Eroot server runner changing config: " + e);
		}
	}

	public void run() {
		System.out.println(this.config.getRootDirectory());
		while (this.running.get()) {
			try {
				server = new MyServer(serverSocket.accept(), this.config);
			} catch (InvalidConfigurationException | IOException e) {
				System.out.println("Eroot server runner start: " + e);
			}
		}

	}

}
