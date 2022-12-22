package main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Monkey {
    private int _name;
    private List<Integer> _items = new ArrayList<>();
    private Function<Integer, Integer> _operation;
    private Function<Integer, Boolean> _test;
    private int _divisibleBy;
    private int[] _sendTo = new int[2]; // 0 = truthy, 1 = falsy
    private int _activity; // items inspected

    Monkey(int name) {
        _name = name;
    }

    List<Integer> getItems() {
        return _items;
    }

    Function<Integer, Integer> getOperation() {
        return _operation;
    }

    Function<Integer, Boolean> getTest() {
        return _test;
    }

    void trackInspection() {
        _activity++;
    }

    int[] getSendTo() {
        return _sendTo;
    }

    int getActivity() {
        return _activity;
    }

    public void setOperation(Function<Integer, Integer> operation) {
        _operation = operation;
    }

    public void setTest(Function<Integer, Boolean> test) {
        _test = test;
    }

    public int getDivisibleBy() {
        return _divisibleBy;
    }

    public void setDivisibleBy(int divisibleBy) {
        _divisibleBy = divisibleBy;
    }
}
