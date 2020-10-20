package server.validators;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class PathValidator {
	private final static int jsonExtensionLength = 5; //.json
	
	public static boolean isValidPath(String path) {
		return PathValidator.isPath(path) && new File(path).exists();	
	}
	
	//*.json format
	public static boolean isJsonFile(String path) {
		if(path.length() <= 5) return false;
		String extension = path.substring(path.length() - jsonExtensionLength);
		return extension.equals(".json");
	}
	
	/**
	 * Checks if a string is a valid path.
	 * Null safe.
	 *  
	 * Calling examples:
	 *    isPath("c:/test");      //returns true
	 *    isPath("c:/te:t");      //returns false
	 *    isPath("c:/te?t");      //returns false
	 *    isPath("c/te*t");       //returns false
	 *    isPath("good.txt");     //returns true
	 *    isPath("not|good.txt"); //returns false
	 *    isPath("not:good.txt"); //returns false
	 */
	public static boolean isPath(String path) {
	    try {
	        Paths.get(path);
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
	    return true;
	}
}
