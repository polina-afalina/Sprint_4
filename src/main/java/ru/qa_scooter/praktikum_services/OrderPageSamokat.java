package ru.qa_scooter.praktikum_services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrderPageSamokat {
    /* Поля */
    // Driver
    private final WebDriver driver;
    static final String ORDERPAGE_URL = "https://qa-scooter.praktikum-services.ru/order";

    // Форма "Для кого самокат"
    // Поле "Имя"
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    // Поле "Фамилия"
    private final By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле "Адрес"
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле "Станция метро"
    private final By metroStationInput = By.xpath(".//input[@placeholder='* Станция метро']");
    // Название станции в дропдауне
    private final By metroStationInDropdown = By.cssSelector("div.select-search__select > :first-child");
    // Поле "Телефон"
    private final By phoneNumberInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    private final By formButtonNext = By.xpath(".//button[text()='Далее']");

    // Форма "Про аренду"
    // Заголовок "Про аренду"
    final By aboutRentTitle = By.xpath(".//div[text()='Про аренду']");
    // Поле "Когда привезти"
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Выбранный день в дейтпикере
    private final By selectedDateInDatePicker = By.cssSelector(".react-datepicker__day--selected");
    // Поле "Срок аренды"
    private final By rentDurationInput = By.xpath(".//div[text()='* Срок аренды']");
    // Опция дропдауна "Срок аренды"
    private final By rentDurationOptions = By.xpath(".//div[@class = 'Dropdown-menu']/div");
    // Чекбокс "Черный жемчуг" в поле "Цвет самоката"
    private final By colorCheckboxBlack = By.id("black");
    // Чекбокс "Серая безысходность" в поле "Цвет самоката"
    private final By colorCheckboxGrey = By.id("grey");
    // Поле "Комментарий для курьера"
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка "Заказать"
    private final By formButtonOrder = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");

    // Модальное окно подтверждения заказа
    // Заголовок "Хотите оформить заказ?"
    final By orderToBeConfirmedText = By.xpath(".//div[text()='Хотите оформить заказ?']");
    // Кнопка "Да"
    private final By orderModalOrderButton = By.xpath(".//button[text()='Да']");
    // Заголовок "Заказ оформлен"
    final By orderConfirmedText = By.xpath(".//div[text()='Заказ оформлен']");

    /* Конструктор */
    public OrderPageSamokat(WebDriver driver) {
        this.driver = driver; // Инициализировали в нём поле driver
    }

    /* Методы */
    // Форма "Для кого самокат"
    // Заполняем поле "Имя"
    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }
    // Заполняем поле "Фамилия"
    public void setLastName(String lastName){
        driver.findElement(lastNameInput).sendKeys(lastName);
    }
    // Заполняем поле "Адрес"
    public void setAddress(String address){
        driver.findElement(addressInput).sendKeys(address);
    }
    // Заполняем поле "Станция метро"
    public void setMetroStation(String metroStation){
        driver.findElement(metroStationInput).sendKeys(metroStation);
        driver.findElement(metroStationInDropdown).click();
    }
    // Заполняем поле "Телефон"
    public void setPhoneNumber(String phoneNumber){
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
    }
    // Нажимаем кнопку "Далее"
    public void clickFormButtonNext() {
        driver.findElement(formButtonNext).click();
    }
    // Шаг: Заполняем форму "Для кого самокат"
    public void fillFormForWhom(String name, String lastName, String address, String metroStation, String phoneNumber){
        setName(name);
        setLastName(lastName);
        setAddress(address);
        setMetroStation(metroStation);
        setPhoneNumber(phoneNumber);
        clickFormButtonNext();
    }

    // Форма "Про аренду"
    // Заполняем поле "Когда привезти"
    public void setDate(String date) {
        driver.findElement(dateInput).sendKeys(date);
        driver.findElement(selectedDateInDatePicker).click();
    }
    // Заполняем поле "Срок аренды"
    public void setRentDuration(int rentDays) {
        // Кликаем по полю, чтобы открыть дропдаун
        driver.findElement(rentDurationInput).click();
        // Получаем список всех опций в дропдауне
        List<WebElement> rentDaysOptions = driver.findElements(rentDurationOptions);
        // Кликаем по нужному элементу
        rentDaysOptions.get(rentDays - 1).click();
    }
    // Выбираем цвет "Черный жемчуг"
    public void selectColorCheckboxBlack(boolean isBlackSelected) {
        if (isBlackSelected) driver.findElement(colorCheckboxBlack).click();
    }
    // Выбираем цвет "Серая безысходность"
    public void selectColorCheckboxGrey(boolean isGreySelected) {
        if (isGreySelected) driver.findElement(colorCheckboxGrey).click();
    }
    // Заполняем поле "Комментарий для курьера"
    public void setComment(String comment){
        driver.findElement(commentInput).sendKeys(comment);
    }
    // Нажимаем кнопку "Заказать"
    public void clickFormButtonOrder() {
        driver.findElement(formButtonOrder).click();
    }
    // Шаг: Заполняем форму "Об аренде"
    public void fillFormAboutRent(String date, int rentDays, boolean isBlackSelected, boolean isGreySelected, String comment){
        setDate(date);
        setRentDuration(rentDays);
        selectColorCheckboxBlack(isBlackSelected);
        selectColorCheckboxGrey(isGreySelected);
        setComment(comment);
        clickFormButtonOrder();
    }
    // Подтверждаем заказ
    public void clickOrderModalOrderButton(){
        driver.findElement(orderModalOrderButton).click();
    }
}