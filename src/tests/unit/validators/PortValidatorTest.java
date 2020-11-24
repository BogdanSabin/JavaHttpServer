package tests.unit.validators;

import static org.junit.Assert.*;

import org.junit.Test;

import server.validators.PortValidator;

public class PortValidatorTest {

	@Test
	public void testPortValidatorPortOkMin() {
		assertTrue("should be true", PortValidator.isValidPort(1025));
	}

	@Test
	public void testPortValidatorPortOkMax() {
		assertTrue("should be true", PortValidator.isValidPort(65535));
	}

	@Test
	public void testPortValidatorPortBadMin() {
		assertFalse("should be false", PortValidator.isValidPort(1025 - 1));
	}

	@Test
	public void testPortValidatorPortBadMax() {
		assertFalse("should be false", PortValidator.isValidPort(65535 + 1));
	}

	@Test
	public void testPortValidatorPortBadOverflowMax() {
		assertFalse("should be false", PortValidator.isValidPort(Integer.MAX_VALUE + 1));
	}

	@Test
	public void testPortValidatorPortBadOverflowMin() {
		assertFalse("should be false", PortValidator.isValidPort(Integer.MIN_VALUE - 1));
	}

}
