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
        assertNull(atmService.banknotCount(Banknot.TWO));
        assertNull(atmService.banknotCount(Banknot.FIVE));
        assertNull(atmService.banknotCount(Banknot.TEN));

        atmService.moneyAdd(Banknot.TWO, 5);
        assertEquals(15, atmService.totalAmountOfMoney());
        assertEquals(5, atmService.banknotCount(Banknot.ONE));
        assertEquals(5, atmService.banknotCount(Banknot.TWO));
        assertNull(atmService.banknotCount(Banknot.FIVE));
        assertNull(atmService.banknotCount(Banknot.TEN));

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
                Banknot.ONE, 1,
                Banknot.TWO, 1,
                Banknot.FIVE, 2,
                Banknot.TEN, 3
        ));

        // проверяем сумму большую находящейся в АТМ
        assertThrowsExactly(NotEnoughMoneyException.class,
                () -> atmService.withdrawSomeMoney(100));

        // проверяем сумму, для которой не можем набрать нужных банкнот из находящихся в АТМ
        assertThrowsExactly(NotEnoughMoneyException.class,
                () -> atmService.withdrawSomeMoney(19));
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

        // проверяем кол-во денег перед снятием
        assertEquals(64, atmService.totalAmountOfMoney());
        assertEquals(12, atmService.banknotCount(Banknot.ONE));
        assertEquals(6, atmService.banknotCount(Banknot.TWO));
        assertEquals(2, atmService.banknotCount(Banknot.FIVE));
        assertEquals(3, atmService.banknotCount(Banknot.TEN));


        val gettedBanknots = atmService.withdrawSomeMoney(18);

        // проверяем кол-во полученных банкнот
        assertEquals(1, gettedBanknots.get(Banknot.ONE));
        assertEquals(1, gettedBanknots.get(Banknot.TWO));
        assertEquals(1, gettedBanknots.get(Banknot.FIVE));
        assertEquals(1, gettedBanknots.get(Banknot.TEN));

        // проверяем кол-во банкнот оставшихся в АТМ
        assertEquals(46, atmService.totalAmountOfMoney());
        assertEquals(11, atmService.banknotCount(Banknot.ONE));
        assertEquals(5, atmService.banknotCount(Banknot.TWO));
        assertEquals(1, atmService.banknotCount(Banknot.FIVE));
        assertEquals(2, atmService.banknotCount(Banknot.TEN));
    }
}