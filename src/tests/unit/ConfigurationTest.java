package tests.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import server.Configuration;
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

}
