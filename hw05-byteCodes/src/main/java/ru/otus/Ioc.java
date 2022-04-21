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

        private final List<Method> implMethods;

        MyInvocationHandler(T myClass) {
            this.myClass = myClass;
            this.implMethods = Arrays.stream(myClass.getClass().getMethods()).toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            var implMethod = implMethods.stream()
                    .filter(iMethod -> compareTwoMethod(iMethod, method))
                    .findFirst().orElse(null);

            if (Objects.nonNull(implMethod) && implMethod.isAnnotationPresent(Log.class)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
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
}
