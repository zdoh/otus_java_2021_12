package ru.otus.homework.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.Banknot;
import ru.otus.homework.exception.NotEnoughMoneyException;
import ru.otus.homework.exception.WrongWithdrawAmountException;
import ru.otus.homework.service.impl.AtmServiceImpl;

class AtmServiceTest {


    @Test
    @DisplayName("Проверяем что добавление банкнот происходит корректно")
    void moneyAdd() {
        AtmService atmService = new AtmServiceImpl();

        atmService.moneyAdd(Banknot.ONE, 5);
        assertEquals(5, atmService.totalAmountOfMoney());
        assertEquals(5, atmService.banknotCount(Banknot.ONE));
        assertEquals(0, atmService.banknotCount(Banknot.TWO));
        assertEquals(0, atmService.banknotCount(Banknot.FIVE));
        assertEquals(0, atmService.banknotCount(Banknot.TEN));

        atmService.moneyAdd(Banknot.TWO, 5);
        assertEquals(15, atmService.totalAmountOfMoney());
        assertEquals(5, atmService.banknotCount(Banknot.ONE));
        assertEquals(5, atmService.banknotCount(Banknot.TWO));
        assertEquals(0, atmService.banknotCount(Banknot.FIVE));
        assertEquals(0, atmService.banknotCount(Banknot.TEN));

        atmService.moneyAdd(Map.of(Banknot.FIVE, 2, Banknot.TEN, 3, Banknot.ONE, 7));
        assertEquals(62, atmService.totalAmountOfMoney());
        assertEquals(12, atmService.banknotCount(Banknot.ONE));
        assertEquals(5, atmService.banknotCount(Banknot.TWO));
        assertEquals(2, atmService.banknotCount(Banknot.FIVE));
        assertEquals(3, atmService.banknotCount(Banknot.TEN));
    }

    @Test
    @DisplayName("Проверяем, что при недостатке денежных средств в АТМ, кидает корректную ошибку")
    void notEnoughMoneyGetException() {
        AtmService atmService = new AtmServiceImpl();
        atmService.moneyAdd(Map.of(
                Banknot.ONE, 12,
                Banknot.TWO, 6,
                Banknot.FIVE, 2,
                Banknot.TEN, 3
        ));

        assertThrowsExactly(NotEnoughMoneyException.class,
                () -> atmService.withdrawSomeMoney(100));
    }

    @Test
    @DisplayName("Проверяем, что при запросе на выдачу некорректной суммы кидается ошибка")
    void wrongWithdrawMoneyGetException() {
        AtmService atmService = new AtmServiceImpl();
        atmService.moneyAdd(Map.of(
                Banknot.ONE, 12,
                Banknot.TWO, 6,
                Banknot.FIVE, 2,
                Banknot.TEN, 3
        ));

        assertThrowsExactly(WrongWithdrawAmountException.class,
                () -> atmService.withdrawSomeMoney(-1));

        assertThrowsExactly(WrongWithdrawAmountException.class,
                () -> atmService.withdrawSomeMoney(0));
    }

    @Test
    @DisplayName("Проверяем, что снятие денежных средств происходит корректно")
    void moneyGet() {
        AtmService atmService = new AtmServiceImpl();
        atmService.moneyAdd(Map.of(
                Banknot.ONE, 12,
                Banknot.TWO, 6,
                Banknot.FIVE, 2,
                Banknot.TEN, 3
        ));

        assertEquals(64, atmService.totalAmountOfMoney());

        val gettedBanknots = atmService.withdrawSomeMoney(18);
        assertEquals(1, gettedBanknots.get(Banknot.ONE));
        assertEquals(1, gettedBanknots.get(Banknot.TWO));
        assertEquals(1, gettedBanknots.get(Banknot.FIVE));
        assertEquals(1, gettedBanknots.get(Banknot.TEN));

        assertEquals(46, atmService.totalAmountOfMoney());
        assertEquals(11, atmService.banknotCount(Banknot.ONE));
        assertEquals(5, atmService.banknotCount(Banknot.TWO));
        assertEquals(1, atmService.banknotCount(Banknot.FIVE));
        assertEquals(2, atmService.banknotCount(Banknot.TEN));
    }
}