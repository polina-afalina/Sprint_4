package ru.qa_scooter.praktikum_services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderFlowTest {
    /* Поля */
    private WebDriver driver;
    private HomePageSamokat homePage;
    private OrderPageSamokat orderPage;
    // Поля параметров
    private final String buttonPosition;
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final int rentDuration;
    private final boolean isBlackSelected;
    private final boolean isGreySelected;
    private final String comment;

    /* Конструктор для параметризации */
    public OrderFlowTest(String buttonPosition, String name, String lastName, String address, String metroStation,
                         String phoneNumber, String deliveryDate, int rentDuration, boolean isBlackSelected,
                         boolean isGreySelected, String comment) {
        this.buttonPosition = buttonPosition;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentDuration = rentDuration;
        this.isBlackSelected = isBlackSelected;
        this.isGreySelected = isGreySelected;
        this.comment = comment;
    }

    // Определяем значения параметров для передачи в конструктор
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {"top", "Мария", "Иванова", "ул. Мира", "Динамо", "88008008080", "30.03.2025", 1, true, true, "Пишите СМС вместо звонка."},
                {"bottom", "Сергей", "Акопян", "Ленина 10", "Верхние котлы", "+79009009090", "01.04.2025", 7, false, false, ""},
                {"top", "Мао", "Ли", "ул. Свердлова, д. 3", "ВДНХ", "00000000000", "30.05.2025", 4, true, false, "Спасибо"}
        };
    }

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
                // Отключаем прокси в Firefox, чтобы предотвратить ошибку "Прокси-сервер отказывается принимать соединения"
                firefoxOptions.addPreference("network.proxy.type", 0);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                // Отключаем прокси в Chrome
                chromeOptions.addArguments("--no-proxy-server");
                chromeOptions.addArguments("--proxy-server='direct://'");
                chromeOptions.addArguments("--proxy-bypass-list=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage = new HomePageSamokat(driver);
        orderPage = new OrderPageSamokat(driver);
        homePage.clickAcceptCookiesButton();
    }

    /* Параметризованный тест */
    @Test
    public void testOrderProcess() {
        // Проверяем, что обе кнопки входа открывают страницу заказа
        homePage.clickOrderButton(buttonPosition);
        assertEquals("По клику на кнопку 'Заказать' не открылась страница заказа!", "https://qa-scooter.praktikum-services.ru/order", driver.getCurrentUrl());

        // Проверяем, что после отправки первой части формы октрывается вторая часть формы
        orderPage.fillFormForWhom(name, lastName, address, metroStation, phoneNumber);
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isFormForWhomCompleted = !driver.findElements(orderPage.aboutRentTitle).isEmpty();
        assertTrue("После отправки формы 'Для кого самокат' не появилась форма 'Про аренду'!", isFormForWhomCompleted);

        // Проверяем, что после отправки второй части формы открывается окно подтверждения заказа
        orderPage.fillFormAboutRent(deliveryDate, rentDuration, isBlackSelected, isGreySelected, comment);
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isFormAboutRentCompleted = !driver.findElements(orderPage.orderToBeConfirmedText).isEmpty();
        assertTrue("После отправки формы 'Про аренду' не появилось модальное окно для подтверждения заказа!", isFormAboutRentCompleted);

        // Проверяем, что после клика по кнопке 'Да' видно сообщение об успешном создании заказа
        orderPage.clickOrderModalOrderButton();
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isOrderConfirmed = !driver.findElements(orderPage.orderConfirmedText).isEmpty();
        assertTrue("После подтверждения заказа не появилось сообщение об успешном создании заказа!", isOrderConfirmed);
    }

    /* Завершение теста */
    @After
    public void tearDown() {
        driver.quit();
    }
}