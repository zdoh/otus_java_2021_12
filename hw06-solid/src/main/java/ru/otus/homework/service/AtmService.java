package ru.otus.homework.service;

import java.util.Map;
import ru.otus.homework.Banknot;

/**
 * Сервис работы АТМ
 */
public interface AtmService {

    /**
     * Добавляем купюры в АТМ по одному типу
     *
     * @param banknot тип купюры
     * @param count кол-во купюр
     */
    void moneyAdd(Banknot banknot, Integer count);

    /**
     *  Добавляем купюры в АТМ кучей
     *
     * @param banknotsWithCount куча купюр различного номинала
     */
    void moneyAdd(Map<Banknot, Integer> banknotsWithCount);

    /**
     * Получить сумму денег в АТМ
     *
     * @return сумма денег в АТМ
     */
    Integer totalAmountOfMoney();

    /**
     * Получить кол-во купюр находящихся в АТП переданного номинала
     *
     * @param banknot номинал банкноты
     * @return кол-во купюр находящихся в АТМ
     */
    Integer banknotCount(Banknot banknot);

    /**
     * Снять некую сумму из АТМ
     *
     * @param amountOfMoney сумму, которую необходимо снять
     * @return номинал и кол-во банкнот, которые получились
     */
    Map<Banknot, Integer> withdrawSomeMoney(Integer amountOfMoney);
}
