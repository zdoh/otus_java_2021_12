package ru.otus.hw03;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.otus.hw03.annotations.After;
import ru.otus.hw03.annotations.Before;
import ru.otus.hw03.annotations.Test;
import ru.otus.hw03.utils.ReflectionHelper;

public class TestLauncher {
    private static final String DELIMITER = "___________________________________";


    public <T> void runTest(Class<T> tClass) {
        List<Method> methods = Arrays.stream(tClass.getMethods()).toList();
        List<Method> allBeforeMethod = getNeededMethodList(methods, Before.class);
        List<Method> allAfterMethod = getNeededMethodList(methods, After.class);
        List<Method> allTestMethod = getNeededMethodList(methods, Test.class);

        List<Boolean> testResult = allTestMethod
                .stream()
                .map(test -> runTestLogic(tClass, test, allBeforeMethod, allAfterMethod))
                .toList();

        printResultMessage(testResult);
    }

    private List<Method> getNeededMethodList(List<Method> methods, Class<? extends Annotation> type) {
        return methods.stream()
                .filter(method -> method.isAnnotationPresent(type))
                .toList();
    }

    private <T> boolean runTestLogic(
            Class<T> tClass,
            Method testMethod,
            List<Method> allBeforeMethod,
            List<Method> allAfterMethod
    ) {
        boolean result = false;
        T clazz = ReflectionHelper.instantiate(tClass);
        try {
            allBeforeMethod.forEach(beforeTest -> ReflectionHelper.callMethod(clazz, beforeTest.getName()));
            ReflectionHelper.callMethod(clazz, testMethod.getName());
            result = true;
        } catch (RuntimeException e) {
            System.out.println("!!!!! во время выполнение теста произошла ошибка: " + e.getMessage()
                    + ", но это не повод перестать работать");
        }
        allAfterMethod.forEach(afterTest -> ReflectionHelper.callMethod(clazz, afterTest.getName()));
        System.out.println(DELIMITER);
        return result;
    }

    private void printResultMessage(List<Boolean> testResult) {
        System.out.println("==============================>");
        System.out.println("Всего было запущено тестов: " + testResult.size());
        System.out.println("Успешно выполнено: " +
                testResult.stream().filter(res -> Objects.equals(res, true)).count());
        System.out.println("Выполнено с ошибкой: " +
                testResult.stream().filter(res -> Objects.equals(res, false)).count());
    }
}
