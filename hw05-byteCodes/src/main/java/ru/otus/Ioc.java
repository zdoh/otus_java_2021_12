package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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

        private final Map<Method, Boolean> implClassMethodContainLogAnnotations;

        MyInvocationHandler(T myClass) {
            this.myClass = myClass;

            this.implClassMethodContainLogAnnotations =
                    Arrays.stream(myClass.getClass().getInterfaces())
                            .map(Class::getMethods)
                            .map(Arrays::asList)
                            .flatMap(List::stream)
                            .collect(Collectors.toMap(method -> method,
                                    method -> Arrays.stream(myClass.getClass().getMethods()).toList().stream()
                                            .filter(implMethod -> compareTwoMethod(method, implMethod))
                                            .anyMatch(implMethod -> implMethod.isAnnotationPresent(Log.class))
                            ));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            if (Objects.equals(implClassMethodContainLogAnnotations.get(method), true)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }
    }

    private static boolean compareTwoMethod(Method first, Method second) {
        // собираем список типов параметров в первом методе
        var firstMethodParamTypeNames = Arrays
                .stream(first.getParameters())
                .map(a -> a.getParameterizedType().getTypeName())
                .toList();

        // собираем список типов параметров во втором методе
        var secondMethodParamTypeNames = Arrays
                .stream(second.getParameters())
                .map(a -> a.getParameterizedType().getTypeName())
                .toList();

        return Objects.equals(first.getName(), second.getName()) &&
                firstMethodParamTypeNames.equals(secondMethodParamTypeNames);
    }
}
