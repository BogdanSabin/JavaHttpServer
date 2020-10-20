package server.configuration;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import server.error.InvalidConfigurationException;
import server.validators.PathValidator;

// I use JSON-simple-1.1 library for input/output operations
// To import jar file in Eclipse IDE, follow the steps given below.
//	1. Right click on project
//	2. Select Configure Build Path
//	3. Open Libraries tab
//	4. Click on Add External JARs
//	5. Select the jar file from the required folder
//	6. Click Apply and OK
public class PersistentConfiguration {
	private String configPath;

	public PersistentConfiguration(String configPath) throws InvalidConfigurationException, IOException {
		if (!PathValidator.isJsonFile(configPath))
			throw new InvalidConfigurationException(configPath + " is not a json file");
		if (!PathValidator.isPath(configPath))
			throw new InvalidConfigurationException(configPath + " invalid path");
		if (!PathValidator.isValidPath(configPath)) // not exists then create it
			this.createJSONFile(configPath);
		
		this.configPath = configPath;
	}

	public void setKey(String key, String value) {
		this.saveIntoStorage(key, value);
	}

	public String getValue(String key) {
		JSONObject obj = this.readFromStorage();
		JSONObject config = (JSONObject) obj.get("config");
		return (String) config.get(key);
	}

	public String getConfigPath() {
		return configPath;
	}

	@SuppressWarnings("unchecked")
	private void saveIntoStorage(String key, String value) {
		JSONObject obj = this.readFromStorage();
		JSONObject config = (JSONObject) obj.get("config");
		config.put(key, value);
		obj.put("config", config);

		try {
			FileWriter myWriter = new FileWriter(this.configPath);
			myWriter.write(obj.toJSONString());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occured while trying to save data: \n" + key + " :" + value
					+ "\n into database: " + this.configPath);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	private JSONObject readFromStorage() {
		JSONParser parser = new JSONParser();
		try (Reader reader = new FileReader(this.configPath)) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONObject config = (JSONObject) jsonObject.get("config");
			if (config == null)
				jsonObject.put("config", new JSONObject());
			return jsonObject;
		} catch (IOException | ParseException e) {
			System.out.println("Error reading from storage: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	private void createJSONFile(String path) throws IOException {
		FileWriter myWriter = new FileWriter(path);
		myWriter.write(new JSONObject().toJSONString());
		myWriter.close();
	}

}
