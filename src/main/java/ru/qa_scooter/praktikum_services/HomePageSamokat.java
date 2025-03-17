package ru.qa_scooter.praktikum_services;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePageSamokat {
    /* Поля */
    // Driver
    private final WebDriver driver;
    static final String HOMEPAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // Кнопка "Принять кукис" в плавающем баннере
    private final By acceptCookiesButton = By.id("rcc-confirm-button");
    // Раздел "Вопросы о важном"
    private final By faqSection = By.className("Home_FourPart__1uthg");
    // Вопрос в разделе "Вопросы о важном"
    private final By faqQuestions = By.xpath(".//div[starts-with(@id, 'accordion__heading-')]");
    // Массив с ожидаемыми вопросами
    final String[] expectedQuestions = {
            "Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?",
            "Я жизу за МКАДом, привезёте?" // На 16.03.2025 на сайте вопрос отображается с этой опечаткой
    };
    // Ответ в разделе "Вопросы о важном"
    private final By faqAnswers = By.xpath(".//div[starts-with(@id, 'accordion__panel-')]");
    // Массив с ожидаемыми ответами
    final String[] expectedAnswers = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };
    // Кнопка "Заказать" вверху страницы
    private final By orderButtonTop = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    // Кнопка "Заказать" внизу страницы
    private final By orderButtonBottom = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");

    /* Конструктор */
    public HomePageSamokat(WebDriver driver) {
        this.driver = driver; // Инициализировали в нём поле driver
    }

    /* Методы */
    // Кликаем по кнопке "Принять кукис"
    public void clickAcceptCookiesButton(){
        driver.findElement(acceptCookiesButton).click();
    }

    // Кнопки "Заказать"
    // Кликаем по верхней кнопке
    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }
    // Кликаем по нижней кнопке
    public void clickOrderButtonBottom() {
        driver.findElement(orderButtonBottom).click();
    }
    // Кликаем по кнопке "Заказать", передавая параметр
    public void clickOrderButton(String buttonPosition) {
        if ("top".equals(buttonPosition)) {
            clickOrderButtonTop();
        } else if ("bottom".equalsIgnoreCase(buttonPosition)) {
            clickOrderButtonBottom();
        } else {
            // Добавляем обработку исключения
            throw new IllegalArgumentException("Некорректное расположение кнопки: " + buttonPosition);
        }
    }

    // Раздел "Вопросы о важном"
    // Получаем список вопросов
    public List<WebElement> getFaqQuestions() {
        return driver.findElements(faqQuestions);
    }
    // Скроллим к разделу "Вопросы о важном", чтобы избежать перекрытия раздела картинкой
    public void scrollToFaqSection() {
        WebElement element = driver.findElement(faqSection);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    // Кликаем по конкретному вопросу по индексу
    public void clickFaqQuestion(int index) {
        getFaqQuestions().get(index).click();          // Кликаем по нему
    }
    // Получаем текст вопроса по индексу
    public String getFaqQuestionText(int index) {
        return getFaqQuestions().get(index).getText().trim();
    }
    // Получаем список ответов
    public List<WebElement> getFaqAnswers() {
        return driver.findElements(faqAnswers);
    }
    // Получаем текст ответа по индексу
    public String getFaqAnswerText(int index) {
        // Добавляем ожидание в 3 секунды
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement answer = getFaqAnswers().get(index);
        // Ждем, когда ответ станет видимым на странице
        wait.until(ExpectedConditions.visibilityOf(answer));
        return answer.getText().trim();
    }
}