package ru.qa_scooter.praktikum_services;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BaseTest {
    /* Поля */
    protected WebDriver driver;
    protected HomePageSamokat homePage;
    protected OrderPageSamokat orderPage;

    /* Настройка теста */
    @Before
    public void setUp() {
        // Получаем значение параметра "browser" (по умолчанию Chrome)
        String browser = System.getProperty("browser", "firefox");
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("network.proxy.type", 0);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--proxy-server='direct://'");
                chromeOptions.addArguments("--proxy-bypass-list=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        // Открываем окно браузера во весь экран
        driver.manage().window().maximize();
        driver.get(HomePageSamokat.HOMEPAGE_URL);
        homePage = new HomePageSamokat(driver);
        orderPage = new OrderPageSamokat(driver);
        homePage.clickAcceptCookiesButton();
    }

    /* Завершение теста */
    @After
    public void tearDown() {
        driver.quit();
    }
}
