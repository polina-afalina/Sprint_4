package ru.qa_scooter.praktikum_services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.*;

public class HomePageFAQTest {
    /* Поля */
    private WebDriver driver;
    private HomePageSamokat homePage;

    /* Настройка теста */
    @Before
    public void setUp() {
        // Получаем значение параметра "browser" (по умолчанию Chrome)
        String browser = System.getProperty("browser", "chrome");
        // Команды для запуска тестов в консоли:
        // mvn test -Dbrowser=firefox
        // mvn test -Dbrowser=chrome
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                // Отключаем прокси в Firefox, чтобы предотвратить возможную ошибку "Прокси-сервер отказывается принимать соединения"
                firefoxOptions.addPreference("network.proxy.type", 0);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                // Отключаем прокси в Chrome, чтобы предотвратить возможную ошибку ERR_PROXY_CONNECTION_FAILED
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--proxy-server='direct://'");
                chromeOptions.addArguments("--proxy-bypass-list=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage = new HomePageSamokat(driver);
    }

    /* Тест */
    @Test
    public void clickFaqQuestionOpensNotEmptyAnswer() {
        // Считаем, сколько всего вопросов в разделе "Вопросы о важном"
        int questionsCount = homePage.getFaqQuestions().size();

        // Перебираем вопросы в цикле
        for (int i = 0; i < questionsCount; i++) {
            // Кликаем по вопросу
            homePage.clickFaqQuestion(i);

            // Проверяем, что ответ открылся (стал видимым)
            assertTrue("Ответ на вопрос №" + i + " не отображается!", homePage.isFaqAnswerVisible(i));

            // Проверяем, что ответ не пустой (содержит текст)
            String answerText = homePage.getFaqAnswerText(i);
            assertFalse("Ответ на вопрос №" + i + " пустой!", answerText.isEmpty());
        }
    }

    /* Завершение теста */
    @After
    public void tearDown() {
        driver.quit();
    }
}
