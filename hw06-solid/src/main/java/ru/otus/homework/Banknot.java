package ru.otus.homework;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Banknot {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10);

    private final int denomination;
}
