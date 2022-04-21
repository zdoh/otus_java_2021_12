package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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

        private final Class<?> impClass;

        MyInvocationHandler(T myClass) {
            this.myClass = myClass;
            this.impClass = myClass.getClass();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            if (impClass.getDeclaredMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Log.class)) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }
}
