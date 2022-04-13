package ru.otus.homework;

import java.util.Stack;

public class CustomerReverseOrder {
    private final Stack<Customer> stack = new Stack<>();

    public void add(Customer customer) {
        stack.add(customer);
    }

    public Customer take() {
        return stack.pop();
    }
}
