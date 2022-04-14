package ru.otus.homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {
    private final Deque<Customer> stack;

    public CustomerReverseOrder() {
        stack = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        stack.add(customer);
    }

    public Customer take() {
        return stack.pollLast();
    }
}
