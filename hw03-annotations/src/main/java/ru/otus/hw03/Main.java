package ru.otus.hw03;

public class Main {
    public static void main(String[] args) {
        TestLauncher testLauncher = new TestLauncher();
        TestClass testClass = new TestClass();
        testLauncher.runTest(testClass.getClass());
    }
}
