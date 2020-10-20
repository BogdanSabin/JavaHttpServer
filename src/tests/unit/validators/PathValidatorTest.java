package tests.unit.validators;

import static org.junit.Assert.*;

import org.junit.Test;

import server.validators.PathValidator;

public class PathValidatorTest {

	@Test
	public void testPathValidatorJSONFile() {
		assertTrue("should be true", PathValidator.isJsonFile("a.json"));
	}
	
	@Test
	public void testPathValidatorJSONFileBadName() {
		assertFalse("should be false", PathValidator.isJsonFile(".json"));
	}
	
	@Test
	public void testPathValidatorJSONFileBadExtension() {
		assertFalse("should be false", PathValidator.isJsonFile("a.txt"));
	}
	
	@Test
	public void testPathValidatorBadPath() {
		assertFalse("should be false", PathValidator.isPath("/\\a.txt"));
	}
	
	@Test
	public void testPathValidatorValidPath() {
		assertTrue("should be true", PathValidator.isPath("a.txt"));
	}
	
	@Test
	public void testPathValidatorValidPathMultiple() {
		assertTrue("should be true", PathValidator.isPath("\\c\\b\\a.txt"));
	}
	
	@Test
	public void testPathValidatorFileExists() {
		assertTrue("should be true", PathValidator.isValidPath("C:\\Temp"));
	}
	
	@Test
	public void testPathValidatorFileNotExists() {
		assertFalse("should be false", PathValidator.isValidPath("X:\\Temp"));
		
	}
	
}
