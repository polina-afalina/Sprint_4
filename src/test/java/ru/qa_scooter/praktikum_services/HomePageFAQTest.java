package ru.qa_scooter.praktikum_services;

import org.junit.Test;
import static org.junit.Assert.*;

public class HomePageFAQTest extends BaseTest{
    /* Тест */
    @Test
    public void clickFaqQuestionOpensCorrectAnswer() {
        // Скроллим до раздела "Вопросы о важном"
        homePage.scrollToFaqSection();

        // Считаем, сколько всего вопросов в разделе "Вопросы о важном"
        int questionsCount = homePage.getFaqQuestions().size();
        // Проверяем, что количество вопросов соответствует ожидаемому
        assertEquals("Количество вопросов не совпадает с ожидаемым!", homePage.expectedQuestions.length, questionsCount);

        // Считаем, сколько всего ответов в разделе "Вопросы о важном"
        int answersCount = homePage.getFaqAnswers().size();
        // Проверяем, что количество ответов соответствует ожидаемому
        assertEquals("Количество вопросов не совпадает с ожидаемым!", homePage.expectedAnswers.length, answersCount);

        // Перебираем вопросы в цикле
        for (int i = 0; i < questionsCount; i++) {
            // Проверяем текст вопроса
            assertEquals("Текст вопроса №" + (i + 1) + " не совпадает с ожидаемым!", homePage.expectedQuestions[i], homePage.getFaqQuestionText(i));

            // Кликаем по вопросу
            homePage.clickFaqQuestion(i);

            // Проверяем текст ответа
            assertEquals("Текст ответа на вопрос №" + (i + 1) + " не совпадает с ожидаемым!", homePage.expectedAnswers[i], homePage.getFaqAnswerText(i));
        }
    }
}