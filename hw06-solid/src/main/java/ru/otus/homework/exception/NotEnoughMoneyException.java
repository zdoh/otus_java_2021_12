package ru.otus.homework.exception;

/**
 * Ошибка вызываемая в случае недостаточности денежных средств в АТМ
 */
public class NotEnoughMoneyException extends RuntimeException {
    private final static String MESSAGE = "Не достаточно денег";

    public NotEnoughMoneyException() {
        super(MESSAGE);
    }
}
