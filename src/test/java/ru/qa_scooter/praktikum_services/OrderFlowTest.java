package ru.qa_scooter.praktikum_services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderFlowTest extends BaseTest{
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

    /* Параметризованный тест */
    @Test
    public void testOrderProcess() {
        // Проверяем, что обе кнопки входа открывают страницу заказа
        homePage.clickOrderButton(buttonPosition);
        assertEquals("По клику на кнопку 'Заказать' не открылась страница заказа!", OrderPageSamokat.ORDERPAGE_URL, driver.getCurrentUrl());

        // Проверяем, что после отправки первой части формы открывается вторая часть формы
        orderPage.fillFormForWhom(name, lastName, address, metroStation, phoneNumber);
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isFormForWhomCompleted = !driver.findElements(orderPage.aboutRentTitle).isEmpty();
        assertTrue("После отправки формы 'Для кого самокат' не появилась форма 'Про аренду'!", isFormForWhomCompleted);

        // Проверяем, что после отправки второй части формы открывается окно подтверждения заказа
        orderPage.fillFormAboutRent(deliveryDate, rentDuration, isBlackSelected, isGreySelected, comment);
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isFormAboutRentCompleted = !driver.findElements(orderPage.orderToBeConfirmedText).isEmpty();
        assertTrue("После отправки формы 'Про аренду' не появилось модальное окно для подтверждения заказа!", isFormAboutRentCompleted);

        // Проверяем, что после клика по кнопке "Да" видно сообщение об успешном создании заказа
        orderPage.clickOrderModalOrderButton();
        // Проверяем, что найденных элементов будет не ноль, чтобы тест не упал при отсутствии элемента
        boolean isOrderConfirmed = !driver.findElements(orderPage.orderConfirmedText).isEmpty();
        assertTrue("После подтверждения заказа не появилось сообщение об успешном создании заказа!", isOrderConfirmed);
    }
}