package ru.calculator;

import java.util.ArrayList;
import java.util.List;

public class Summator {
    private Integer sum = 0;
    private Integer prevValue = 0;
    private Integer prevPrevValue = 0;
    private Integer sumLastThreeValues = 0;
    private Integer someValue = 0;
    private final List<Data> listValues = new ArrayList<>();

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        listValues.add(data);
        if (listValues.size() % 6_600_000 == 0) {
            listValues.clear();
        }
        sum += data.getValue();

        sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();

        // вариант 1, где мы убрали не нужное сохранения в someValue
        /*for (var idx = 0; idx < 3; idx++) {
            // первоначальный вариант
            // someValue += (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
            // someValue = Math.abs(someValue) + listValues.size();
            someValue = Math.abs(someValue + (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum))
                    + listValues.size();
        }*/

        // вариант 2, где мы присваиваем значение someValue без цикла и предварительного сохранения в someValue
        someValue = Math.abs(
                Math.abs(
                        Math.abs(
                                someValue + (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum)
                        ) + listValues.size() + (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum)
                ) + listValues.size() + (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum)
        ) + listValues.size();
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getPrevValue() {
        return prevValue;
    }

    public Integer getPrevPrevValue() {
        return prevPrevValue;
    }

    public Integer getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public Integer getSomeValue() {
        return someValue;
    }
}
