package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ru.otus.annotation.Log;

public class Ioc {

    private Ioc() {
    }

    static TestLogging createMyClass() {
        MyInvocationHandler<TestLogging> handler = new MyInvocationHandler<>(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class},
                handler
        );
    }

    static class MyInvocationHandler<T> implements InvocationHandler {

        private final T myClass;

        private final Class<?> implementation;

        MyInvocationHandler(T myClass) {
            this.myClass = myClass;
            this.implementation = myClass.getClass();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            // находим нужный метод в классе, который имплементирует интерфейс
            var implMethod = Arrays.stream(implementation.getMethods())
                    .filter(iMethod -> compareTwoMethod(iMethod, method))
                    .findFirst().orElse(null);

            if (Objects.nonNull(implMethod) && implMethod.isAnnotationPresent(Log.class)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }

    private static boolean compareTwoMethod(Method first, Method second) {
        // собираем список типов параметров в первом методе
        var firstParam = Arrays
                .stream(first.getParameters())
                .map(a -> a.getParameterizedType().getTypeName())
                .toList();

        // собираем список типов параметров во втором методе
        var secondParam = Arrays
                .stream(second.getParameters())
                .map(a -> a.getParameterizedType().getTypeName())
                .toList();

        return Objects.equals(first.getName(), second.getName()) &&
                firstParam.equals(secondParam);
    }
}
