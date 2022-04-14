package ru.otus.hw03;

import ru.otus.hw03.annotations.After;
import ru.otus.hw03.annotations.Before;
import ru.otus.hw03.annotations.Test;

public class TestClass {

    @Before
    public void beforeTestMethod1() {
        System.out.println("метод1 перед тестированием");
    }

    @Before
    public void beforeTestMethod2() {
        System.out.println("метод2 перед тестированием");
    }

    @Test
    public void test1() {
        System.out.println("запускаем тест1");
        System.out.println("тест1 прошел");
    }

    @Test
    public void test2() {
        System.out.println("запускаем тест2");
        throw new RuntimeException("какая-та ошибка");
        // System.out.println("тест2 прошел");
    }

    @Test
    public void test3() {
        System.out.println("запускаем тест3");
        System.out.println("тест3 прошел");
    }

    @After
    public void afterTestMethod1() {
        System.out.println("метод1 после тестирования");
    }

    @After
    public void afterTestMethod2() {
        System.out.println("метод2 после тестирования");
    }
}
