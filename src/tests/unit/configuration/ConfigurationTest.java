package tests.unit.configuration;

import static org.junit.Assert.*;

import org.junit.Test;

import server.configuration.Configuration;
import server.error.InvalidConfigurationException;

public class ConfigurationTest {

	@Test
	public void testConfigurationInstantiationWithCorrectParameters() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			assertNotNull("should not be null", config);
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationInstantiationWithWrongRoothPath() throws InvalidConfigurationException {
		Configuration config = new Configuration("X:\\Users123", "C:\\Users", 7071);
		assertNull("should be null", config);
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationInstantiationWithWrongMaintenanceFilePath() throws InvalidConfigurationException {
		Configuration config = new Configuration("X:\\Users", "C:\\Users123", 7071);
		assertNull("should be null", config);
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationInstantiationWithWrongLowerPortNumber() throws InvalidConfigurationException {
		Configuration config = new Configuration("X:\\Users", "C:\\Users", 10);
		assertNull("should be null", config);
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationInstantiationWithWrongHigherPortNumber() throws InvalidConfigurationException {
		Configuration config = new Configuration("X:\\Users", "C:\\Users", 10000000);
		assertNull("should be null", config);
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationInstantiationWithNull() throws InvalidConfigurationException {
		Configuration config = new Configuration(null, null, 10000000);
		assertNull("should be null", config);
	}
	
	@Test
	public void testConfigurationSetRootDirectoryCorrectParameter() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			config.setRootDirectory("C:\\Temp");
			assertNotNull("should not be null", config);
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetRootDirectoryWrongParameter() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setRootDirectory("X:\\Temp");		
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetRootDirectoryWrongParameterInvalidPath() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setRootDirectory("X:\\?-.^&*Temp");		
	}
	
	
	@Test
	public void testConfigurationGetRootDirectory() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			String rootDirectory = "C:\\Temp";
			config.setRootDirectory(rootDirectory);
			assertTrue("should be equal", config.getRootDirectory().equals(rootDirectory));
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test
	public void testConfigurationSetMaintenancePageCorrectParameter() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			config.setMaintenancePage("C:\\Temp");
			assertNotNull("should not be null", config);
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetMaintenancePageWrongParameter() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setMaintenancePage("X:\\Temp");		
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetMaintenancePageWrongParameterInvalidPath() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setMaintenancePage("X:\\-?_.Temp");		
	}
	
	@Test
	public void testConfigurationGetMaintenancePage() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			String maintenancePage = "C:\\Temp";
			config.setMaintenancePage(maintenancePage);
			assertTrue("should be equal", config.getMaintenancePage().equals(maintenancePage));
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test
	public void testConfigurationSetPortCorrectParameter() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			config.setPort(8080);
			assertNotNull("should not be null", config);
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetPortWrongParameterLower() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setPort(0);		
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testConfigurationSetPortWrongParameterHeigher() throws InvalidConfigurationException {
		Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
		config.setPort(1000000);		
	}
	
	
	@Test
	public void testConfigurationGetPort() {
		try {
			Configuration config = new Configuration("C:\\Users", "C:\\Users", 7071);
			int port = 8080;
			config.setPort(port);
			assertTrue("should be equal", config.getPort() == port);
			
		} catch (InvalidConfigurationException e) {
			fail("It should not fail with correct parameters");
		}
	}
	
}
