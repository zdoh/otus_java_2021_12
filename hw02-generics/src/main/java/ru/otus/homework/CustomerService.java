package ru.otus.homework;


import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return buildResultEntryOrNull(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return buildResultEntryOrNull(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> buildResultEntryOrNull(Map.Entry<Customer, String> result) {
        if (Objects.nonNull(result)) {
            return Map.entry(
                    new Customer(result.getKey().getId(), result.getKey().getName(), result.getKey().getScores()),
                    result.getValue());
        }

        return null;
    }
}
