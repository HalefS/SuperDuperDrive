package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private HomePage homePage;
	private SignupPage signupPage;
	private ResultPage resultPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void unauthorizedUserTest() {
		navigateTo("files");
		assertEquals(driver.getTitle(), "Login");
		navigateTo("login");
		assertEquals(driver.getTitle(), "Login");
		navigateTo("credentials");
		assertEquals(driver.getTitle(), "Login");
	}

	@Test
	public void loginPageTest() {
		navigateTo("login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unregisteredUserLoginTest() {
		loginPage = new LoginPage(driver);
		navigateTo("login");
		loginPage.login("username", "password");
		assertTrue(loginPage.loginFailed(driver));
	}

	@Test
	public void loginLogoutTest() {
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		navigateTo("signup");
		signupPage.singup("user", "user");
		navigateTo("login");
		loginPage.login("user", "user");
		navigateTo("");
		homePage.logout();
		assertEquals("Login", driver.getTitle());

	}

	@Test
	public void userRegistrationTest() {
		signupPage = new SignupPage(driver);
		navigateTo("signup");
		signupPage.singup("signup", "signup");
		loginPage = new LoginPage(driver);
		navigateTo("login");
		loginPage.login("signup", "signup");
		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void signupTest() {
		navigateTo("signup");
		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void noteCreationTest() {
		signUpAndLogin("user", "example");
		String noteTitle = "example";
		String noteDescription = "exampleDescription";
		homePage.createNote(noteTitle, noteDescription);
		resultPage.OK();
		assertTrue(homePage.noteExists(noteTitle));
	}

	@Test
	public void credentialCreationTest() {
		signUpAndLogin("user", "password");
		String url = "github.com";
		homePage.createCredential(url, "HalefS", "password", driver);
		resultPage.OK();
		assertTrue(homePage.credentialExists(url));
	}

	@Test
	public void noteEditionTest() {
		signUpAndLogin("example", "user");
		homePage.createNote("example", "example");
		resultPage.OK();
		assertTrue(homePage.noteExists("example"));
		homePage.editNote("title", "example", "exampleEdit");
		resultPage.OK();
		assertTrue(homePage.noteExists("exampleEdit"));
	}

	public void signUpAndLogin(String username, String password) {
		navigateTo("signup");
		signupPage.singup(username, password);
		navigateTo("login");
		loginPage.login(username, password);
	}
	
	private void navigateTo(String endpoint) {
		driver.navigate().to(String.format("http://localhost:%d/%s", port, endpoint));
	}

}
