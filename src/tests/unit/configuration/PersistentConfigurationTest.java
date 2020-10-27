package tests.unit.configuration;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import server.configuration.PersistentConfiguration;
import server.errors.InvalidConfigurationException;

public class PersistentConfigurationTest {
	private final String pathToStorageDesktop = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "storage.json";
	
	@Test(expected=InvalidConfigurationException.class)
	public void testPersistentConfigurationWithNonJSONFile() throws InvalidConfigurationException, IOException {
		PersistentConfiguration cfg = new PersistentConfiguration(this.pathToStorageDesktop.replace(".json", ".txt"));
		assertNull("should be null", cfg);
	}
	
	@Test(expected=InvalidConfigurationException.class)
	public void testPersistentConfigurationWithInvalidePath() throws InvalidConfigurationException, IOException {
		PersistentConfiguration cfg = new PersistentConfiguration(this.pathToStorageDesktop + "\\?43432145(*^%432542");
		assertNull("should be null", cfg);
	}
	
	@Test
	public void testPersistentConfigurationWithNONExistingJSONFile() {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop.replace(".json", "2.json"));
			assertNotNull("should not be null", cfg);
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPersistentConfigurationWithExistingJSONFile() {
		PersistentConfiguration cfg;
		try {
			File file = new File(this.pathToStorageDesktop.replace(".json", "2.json"));
			file.createNewFile();
			cfg = new PersistentConfiguration(this.pathToStorageDesktop.replace(".json", "2.json"));
			assertNotNull("should not be null", cfg);
			file.delete();
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPersistentConfigurationSettingAndReceivingKeys() {
		this.testPersistentConfigurationSettingKeys();
		this.testPersistentConfigurationReceivingKeys();
		File file = new File(this.pathToStorageDesktop);
		file.delete();
	}
	
	private void testPersistentConfigurationSettingKeys() {
		PersistentConfiguration cfg;
		try {
			String key1 = "key1"; String val1 = "val1";
			String key2 = "key2"; String val2 = "val2";
			String key3 = "key3"; int val3 = 123;
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			cfg.setKey(key1, val1);
			cfg.setKey(key2, val2);
			cfg.setKey(key3, val3 + "");			
			assertNotNull("should not be null", cfg);
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}
	
	private void testPersistentConfigurationReceivingKeys() {
		PersistentConfiguration cfg;
		try {
			String key1 = "key1"; String val1 = "val1";
			String key2 = "key2"; String val2 = "val2";
			String key3 = "key3"; int val3 = 123;
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			assertNotNull("should not be null", cfg);
		
			assertTrue("should be equal", cfg.getValue(key1).equals(val1));
			assertTrue("should be equal", cfg.getValue(key2).equals(val2));
			assertTrue("should be equal", Integer.parseInt(cfg.getValue(key3)) == val3);
		
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

}
