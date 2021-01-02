package tests.unit.configuration;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import server.configuration.PersistentConfiguration;
import server.errors.InvalidConfigurationException;
import server.errors.InvalidParameterException;

public class PersistentConfigurationTest {
	private final String pathToStorageDesktop = System.getProperty("user.home") + File.separator + "Desktop"
			+ File.separator + "storage.json";

	@After
	public void tearDown() {
		File file = new File(this.pathToStorageDesktop);
		if (file.exists())
			file.delete();
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testPersistentConfigurationWithNonJSONFile() throws InvalidConfigurationException, IOException {
		PersistentConfiguration cfg = new PersistentConfiguration(this.pathToStorageDesktop.replace(".json", ".txt"));
		assertNull("should be null", cfg);
	}

	@Test(expected = InvalidConfigurationException.class)
	public void testPersistentConfigurationWithInvalidePath() throws InvalidConfigurationException, IOException {
		PersistentConfiguration cfg = new PersistentConfiguration(this.pathToStorageDesktop + "\\?43432145(*^%432542");
		assertNull("should be null", cfg);
	}

	@Test
	public void testPersistentConfigurationWithNONExistingJSONFile() {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
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
			File file = new File(this.pathToStorageDesktop);
			file.createNewFile();
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			assertNotNull("should not be null", cfg);
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test
	public void testPersistentConfigurationGetConfigPath() {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			assertNotNull("should not be null", cfg);
			assertTrue("should be equal", this.pathToStorageDesktop.equals(cfg.getConfigPath()));
		} catch (InvalidConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void testPersistentConfigurationSettingNullKey() throws InvalidParameterException {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			cfg.setKey(null, "value");
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void testPersistentConfigurationSettingNullValue() throws InvalidParameterException {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			cfg.setKey("key1", null);
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidParameterException.class)
	public void testPersistentConfigurationGettingWithNullKey() throws InvalidParameterException {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			cfg.getValue(null);
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test
	public void testPersistentConfigurationGettingValueWhichIsNotPresent() throws InvalidParameterException {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			String value = cfg.getValue("valNotPresentInConfig");
			assertTrue("Should be equal", value == null);
		} catch (IOException | InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	@Test
	public void testPersistentConfigurationSettingAndReceivingKeys() {
		String[] keys = { "key1", "key2", "key3" };
		String[] vals = { "val1", "val2", "val3" };
		this.testPersistentConfigurationSettingKeys(keys, vals);
		this.testPersistentConfigurationReceivingKeys(keys, vals);
	}

	private void testPersistentConfigurationSettingKeys(String[] keys, String[] vals) {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			for (int i = 0; i < keys.length; i++) {
				if (vals[i] != null)
					cfg.setKey(keys[i], vals[i]);
			}
			assertNotNull("should not be null", cfg);
		} catch (InvalidConfigurationException | IOException | InvalidParameterException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

	private void testPersistentConfigurationReceivingKeys(String[] keys, String[] vals) {
		PersistentConfiguration cfg;
		try {
			cfg = new PersistentConfiguration(this.pathToStorageDesktop);
			assertNotNull("should not be null", cfg);
			for (int i = 0; i < keys.length; i++) {
				if (vals[i] != null)
					assertTrue("should be equal", cfg.getValue(keys[i]).equals(vals[i]));
			}
		} catch (InvalidConfigurationException | IOException | InvalidParameterException e) {
			// TODO Auto-generated catch block
			fail("Should not fail with good parameters");
			e.printStackTrace();
		}
	}

}
