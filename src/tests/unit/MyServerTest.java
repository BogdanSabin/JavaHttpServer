package tests.unit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.MyServer;
import server.ServerState;
import server.configuration.Configuration;
import server.errors.InvalidConfigurationException;

public class MyServerTest {
	private Configuration config;
	private ServerSocket serverSocket;
	private final int PORT = 7090;

	@Before
	public void setUp() {
		try {

			config = new Configuration("C:\\Users", "C:\\Users", PORT);
			this.serverSocket = new ServerSocket(PORT);
		} catch (InvalidConfigurationException | IOException e) {
			System.out.println("Error setUp: " + e);
			try {
				serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@After
	public void tearDown() {
		try {
			this.serverSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test(timeout = 5000)
	public void testMyServerInstantiationWithCorrectParameters() {
		try {
			this.makeServerRequest();
			MyServer server = new MyServer(this.serverSocket.accept(), this.config);
			assertNotNull("should not be null", server);
		} catch (InvalidConfigurationException | IOException e) {
			fail("It should not fail with correct parameters");
		}
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testMyServerInstantiationWithNullParameters() throws InvalidConfigurationException {
		MyServer server = new MyServer(null, null);
		assertNull("should be null", server);
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testMyServerSetConfigWithInvalidConfig() throws InvalidConfigurationException {
		MyServer.setConfig(null);
	}

	@Test
	public void testMyServerSetAndGetConfigWithValidConfig() {
		try {
			MyServer.setConfig(this.config);
			assertTrue("should be equal", MyServer.getConfig().equals(this.config));
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMyServerGetConfigWithModifiedConfig() {
		try {
			MyServer.setConfig(this.config);
			Configuration temp = new Configuration("C:\\Users", "C:\\Users", 8032);
			assertFalse("should be equal", MyServer.getConfig().equals(temp));
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test
	public void testMyServerStateIsStoppedBeforeInstanciation() {
		assertTrue("Should be equal", MyServer.getServerState() == ServerState.STOPPED);
	}

	@Test
	public void testMyServerSetState() {
		MyServer.setState(ServerState.MAINTENANCE);
		assertTrue("Should be equal", MyServer.getServerState() == ServerState.MAINTENANCE);
	}

	@Test(timeout = 5000)
	@SuppressWarnings("unused")
	public void testMyServerStateIsRunningAfterInstanciation() {
		try {
			MyServer server;
			MyServer.setState(ServerState.RUNNING);
			this.makeServerRequest();
			server = new MyServer(this.serverSocket.accept(), this.config);
			assertTrue("Should be equal", MyServer.getServerState() == ServerState.RUNNING);
		} catch (InvalidConfigurationException | IOException e) {
			fail("It should not fail with correct parameters");
		}
	}

	// makes a simple get request to the server in order to receive a connection
	private void makeServerRequest() {
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				URL url;
				try {
					url = new URL("http://localhost:" + PORT + "/");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.getInputStream();
				} catch (IOException e) {
					System.out.println("Error request: " + e);
				}
			}
		}, 2500);
	}
}
