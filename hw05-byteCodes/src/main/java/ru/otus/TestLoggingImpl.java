package ru.otus;

import ru.otus.annotation.Log;

public class TestLoggingImpl implements TestLogging {

    @Override
    @Log
    public void calculation(int param) {
        System.out.println("некоторое действие в calculation(int param)");
    }

    @Override
    public void calculation(long param) {
        System.out.println("некоторое действие в calculation(long param)");
    }

    @Override
    public void calculation(int param, int param2) {
        System.out.println("некоторое действие в calculation(int param, int param2)");
    }

    @Log
    @Override
    public void calculation(int param, int param2, int param3) {
        System.out.println("некоторое действие в calculation(int param, int param2, int param3)");
    }

    @Override
    public void runAll() {
        calculation(1);
        calculation(1, 2);
        calculation(1, 2,3);
    }
}
