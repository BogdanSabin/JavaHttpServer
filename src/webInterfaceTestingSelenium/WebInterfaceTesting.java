package webInterfaceTestingSelenium;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class WebInterfaceTesting {

	private WebDriver driver;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		driver = new FirefoxDriver();
	}

	@After
	public void tearDown() {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

	@Test
	public void TestNotFoundPage() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1936, 1056));
		driver.findElement(By.linkText("test page")).click();
		assertThat(driver.findElement(By.linkText("Not existing file")).getText(), is("Not existing file"));
		driver.findElement(By.linkText("Not existing file")).click();
		assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("404"));
		driver.findElement(By.cssSelector("h2")).click();
		assertThat(driver.findElement(By.cssSelector("h2")).getText(), is("Page Not Found"));
		driver.findElement(By.cssSelector("html")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("Home Page."));
			assert (elements.size() > 0);
		}
		driver.findElement(By.linkText("Home Page.")).click();
		assertThat(driver.getTitle(), is("Java HTTP server"));
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void TestElementsFromTestPage() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(2) img"));
			assert (elements.size() > 0);
		}
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(3) img"));
			assert (elements.size() > 0);
		}
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(4) img"));
			assert (elements.size() > 0);
		}
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(6) img"));
			assert (elements.size() > 0);
		}
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(7) img"));
			assert (elements.size() > 0);
		}
		assertThat(driver.getTitle(), is("Welcome!"));
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("tr:nth-child(1) img"));
			assert (elements.size() > 0);
		}
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void textFileLink() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		driver.findElement(By.linkText("do TXT files work")).click();
		assertThat(driver.findElement(By.cssSelector("pre")).getText(), is("Hello TXT works"));
	}

	@Test
	public void testUrlWithSpacePage() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("do URLs with spaces work"));
			assert (elements.size() > 0);
		}
		driver.findElement(By.linkText("do URLs with spaces work")).click();
		driver.findElement(By.cssSelector("html")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("back"));
			assert (elements.size() > 0);
		}
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void testUrlWithProcent20Page() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		assertThat(driver.findElement(By.linkText("do URLs with %20 work")).getText(), is("do URLs with %20 work"));
		driver.findElement(By.linkText("do URLs with %20 work")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("back"));
			assert (elements.size() > 0);
		}
		driver.findElement(By.linkText("back")).click();
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void testExternalLink() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		assertThat(driver.findElement(By.linkText("do external links work?")).getText(), is("do external links work?"));
		driver.findElement(By.linkText("do external links work?")).click();
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void testSimpleLinkPage() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(1382, 744));
		driver.findElement(By.linkText("test page")).click();
		assertThat(driver.findElement(By.linkText("do simple relative internal links work?")).getText(),
				is("do simple relative internal links work?"));
		driver.findElement(By.linkText("do simple relative internal links work?")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("back"));
			assert (elements.size() > 0);
		}
		driver.findElement(By.linkText("back")).click();
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Test
	public void testAbsoluteLink() {
		driver.get("http://localhost:8081/");
		driver.manage().window().setSize(new Dimension(951, 603));
		driver.findElement(By.linkText("test page")).click();
		driver.findElement(By.cssSelector("center")).click();
		assertThat(driver.findElement(By.linkText("do general absolute links work")).getText(),
				is("do general absolute links work"));
		driver.findElement(By.linkText("do general absolute links work")).click();
		{
			List<WebElement> elements = driver.findElements(By.linkText("back"));
			assert (elements.size() > 0);
		}
		driver.findElement(By.linkText("back")).click();
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

}
