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

        private final Map<Method, Boolean> implClassMethodContainLogAnnotationMap;

        MyInvocationHandler(T myClass) {
            this.myClass = myClass;

            this.implClassMethodContainLogAnnotationMap =
                    Arrays.stream(myClass.getClass().getInterfaces())
                            .map(Class::getMethods)
                            .map(Arrays::asList)
                            .flatMap(List::stream)
                            .collect(Collectors.toMap(method -> method,
                                    method -> {
                                        try {
                                            return myClass.getClass().getDeclaredMethod(method.getName(),
                                                    method.getParameterTypes()).isAnnotationPresent(Log.class);
                                        } catch (NoSuchMethodException e) {
                                            return false;
                                        }
                                    }));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            if (Objects.equals(implClassMethodContainLogAnnotationMap.get(method), true)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }

            return method.invoke(myClass, args);
        }
    }
}
