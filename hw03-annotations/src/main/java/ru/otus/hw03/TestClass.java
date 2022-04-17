package ru.otus.hw03;

import java.util.Random;
import ru.otus.hw03.annotations.After;
import ru.otus.hw03.annotations.Before;
import ru.otus.hw03.annotations.Test;

public class TestClass {

    @Before
    public void beforeTestMethod1() {
        System.out.println("метод1 перед тестированием запускает функционал");
        System.out.println("метод1 перед тестированием выполнил функционал");
    }

    @Before
    public void beforeTestMethod2() {
        System.out.println("метод2 перед тестированием запускает функционал");
        if (new Random().nextBoolean()) {
            throw new RuntimeException("какая-та ошибка в beforeTestMethod2");
        } else {
            System.out.println("метод2 перед тестированием выполнил функционал");
        }
    }

    @Test
    public void test1() {
        System.out.println("запускаем тест1");
        System.out.println("тест1 прошел");
    }

    @Test
    public void test2() {
        System.out.println("запускаем тест2");
        throw new RuntimeException("какая-та ошибка в test2()");
        // System.out.println("тест2 прошел");
    }

    @Test
    public void test3() {
        System.out.println("запускаем тест3");
        System.out.println("тест3 прошел");
    }

    @After
    public void afterTestMethod1() {
        System.out.println("метод1 после тестирования запускает функционал");
        System.out.println("метод1 после тестирования выполнил функционал");
    }

    @After
    public void afterTestMethod2() {
        System.out.println("метод2 после тестирования запускает функционал");
        System.out.println("метод2 после тестирования выполнил функционал");
    }
}
