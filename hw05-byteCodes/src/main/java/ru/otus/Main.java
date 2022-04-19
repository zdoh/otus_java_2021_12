package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestLogging myClass = Ioc.createMyClass();

        myClass.calculation(1);
        System.out.println();
        myClass.calculation(1, 2);
        System.out.println();
        myClass.calculation(1, 2, 3);
        System.out.println();
        myClass.runAll();
    }
}
