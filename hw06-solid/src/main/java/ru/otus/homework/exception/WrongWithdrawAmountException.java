package ru.otus.homework.exception;

/**
 * Ошибка вызываемая в случае некорректно введенной суммы на выдачу
 */
public class WrongWithdrawAmountException extends RuntimeException {
    private final static String MESSAGE = "Не корректно введенная сумма на выдачу. Сумма должны быть больше 0";

    public WrongWithdrawAmountException() {
        super(MESSAGE);
    }
}
