package ru.otus.homework.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import lombok.Data;
import ru.otus.homework.Banknot;
import ru.otus.homework.exception.NotEnoughMoneyException;
import ru.otus.homework.exception.WrongWithdrawAmountException;
import ru.otus.homework.service.AtmService;

@Data
public class AtmServiceImpl implements AtmService {

    private final Map<Banknot, Integer> banknotsCountMap = new TreeMap<>(
            Comparator.comparing(Banknot::getDenomination, Comparator.reverseOrder()));

    @Override
    public void moneyAdd(Banknot banknot, Integer count) {
        banknotsCountMap.merge(banknot, count, Integer::sum);
    }

    @Override
    public void moneyAdd(Map<Banknot, Integer> banknotsWithCount) {
        for (Entry<Banknot, Integer> banknotIntegerEntry : banknotsWithCount.entrySet()) {
            moneyAdd(banknotIntegerEntry.getKey(), banknotIntegerEntry.getValue());
        }
    }

    @Override
    public int totalAmountOfMoney() {
        return banknotsCountMap.entrySet().stream()
                .map(entry -> entry.getKey().getDenomination() * entry.getValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer banknotCount(Banknot banknot) {
        return banknotsCountMap.getOrDefault(banknot, null);
    }

    @Override
    public Map<Banknot, Integer> withdrawSomeMoney(Integer amountOfMoney) {
        if (amountOfMoney <= 0) {
            throw new WrongWithdrawAmountException();
        }

        if (amountOfMoney > totalAmountOfMoney()) {
            throw new NotEnoughMoneyException();
        }

        Map<Banknot, Integer> tmpCell = new HashMap<>();

        for (Entry<Banknot, Integer> banknotIntegerEntry : banknotsCountMap.entrySet()) {
            if (amountOfMoney != 0) {
                int tmpCnt = Math.min(banknotIntegerEntry.getValue(),
                        amountOfMoney / banknotIntegerEntry.getKey().getDenomination());
                amountOfMoney -= tmpCnt * banknotIntegerEntry.getKey().getDenomination();
                tmpCell.put(banknotIntegerEntry.getKey(), tmpCnt);
            }
        }

        if (amountOfMoney == 0) {
            for (Entry<Banknot, Integer> banknotIntegerEntry : tmpCell.entrySet()) {
                banknotsCountMap.put(banknotIntegerEntry.getKey(),
                        banknotsCountMap.get(banknotIntegerEntry.getKey()) - banknotIntegerEntry.getValue());
            }
            return tmpCell;
        } else {
            throw new NotEnoughMoneyException();
        }
    }
}
