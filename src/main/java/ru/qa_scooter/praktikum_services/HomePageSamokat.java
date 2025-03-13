package ru.qa_scooter.praktikum_services;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePageSamokat {
    /* Поля */
    // Driver
    private final WebDriver driver;
    // Кнопка "Принять кукис" в плавающем баннере
    private final By acceptCookiesButton = By.id("rcc-confirm-button");
    // Вопрос в разделе "Вопросы о важном"
    private final By faqQuestions = By.xpath(".//button[starts-with(@id, 'accordion__heading-')]");
    // Ответ в разделе "Вопросы о важном"
    private final By faqAnswers = By.xpath(".//button[starts-with(@id, 'accordion__panel-')]");
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
    // Получаем список ответов
    public List<WebElement> getFaqAnswers() {
        return driver.findElements(faqAnswers);
    }
    // Кликаем по конкретному вопросу по индексу
    public void clickFaqQuestion(int index) {
        getFaqQuestions().get(index).click();
    }
    // Получаем текста ответа по индексу
    public String getFaqAnswerText(int index) {
        return getFaqAnswers().get(index).getText().trim();
    }
    // Проверяем видимость ответа по индексу
    public boolean isFaqAnswerVisible(int index) {
        return getFaqAnswers().get(index).isDisplayed();
    }
}
