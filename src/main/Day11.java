package main;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {
    public static void main(String[] args) {
        FileReader fr = new FileReader();
        String input = fr.readLines("main/day11.input");
        doPart1(input);
    }

    private static void doPart1(String input) {
        Utils.log("main.Day11 Part 1");
        int monkeyBusiness = computePart1(input);
        Utils.log("MonkeyBusiness: " + monkeyBusiness);
    }

    private static int computePart1(String input) {
        List<Monkey> monkeyList = parseMonkey(input);
        int round = 1;
        while (round <= 20) {
            executeRound(monkeyList);
            round++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int idx = 0; idx < monkeyList.size(); idx++) {
            maxHeap.add(monkeyList.get(idx).getActivity());
        }

        return computeMonkeyBusiness(maxHeap, 2);
    }

    private static int computeMonkeyBusiness(PriorityQueue<Integer> maxHeap, int k) {
        int monkeyBusiness = 1;
        while (k > 0) {
            monkeyBusiness *= maxHeap.poll();
            k--;
        }
        return monkeyBusiness;
    }

    private static void executeRound(List<Monkey> monkeyList) {
        for (Monkey monkey : monkeyList) {
            while (!monkey.getItems().isEmpty()) {
                int item = monkey.getItems().remove(0);
                monkey.trackInspection();
                // inspect and operate
                int value = monkey.getOperation().apply(item);
                // relief
                value = value / 3;
                // test
                boolean isTruthy = test(monkey, value);
                // sendTo
                send(isTruthy, monkey, value, monkeyList);
            }

        }
    }

    private static boolean test(Monkey monkey, int value) {
        return value % monkey.getDivisibleBy() == 0;
    }

    private static void send(boolean testResult, Monkey monkey, int value, List<Monkey> monkeyList) {
        int[] sendTo = monkey.getSendTo();
        int toSend = testResult ? sendTo[0] : sendTo[1];
        monkeyList.get(toSend).getItems().add(value);
    }

    private static List<Monkey> parseMonkey(String input) {
        String[] lines = input.split("\n");
        List<Monkey> monkeys = new ArrayList<>();
        Monkey current = null;
        for (String line : lines) {
            String trimmed = line.trim();
            String[] parts = trimmed.split(" ");
            switch (parts[0]) {
                case "main.Monkey":
                    current = parseMonkeyLine(parts[1]);
                    break;
                case "Starting":
                    assert current != null;
                    current.getItems().addAll(parseItemsLine(trimmed));
                    break;
                case "Operation:":
                    assert current != null;
                    current.setOperation(parseOperationLine(trimmed));
                    break;
                case "Test:":
                    assert current != null;
                    current.setDivisibleBy(parseDivisibleBy(trimmed));
                    break;
                case "If":
                    assert current != null;
                    if (trimmed.startsWith("If true")) {
                        current.getSendTo()[0] = parseSendToTruthy(trimmed);
                    } else {
                        current.getSendTo()[1] = parseSendToFalsy(trimmed);
                    }
                    break;
                case "If false:":
                    assert current != null;
                    current.getSendTo()[1] = parseSendToFalsy(trimmed);
                    break;
                default: // empty
                    monkeys.add(current);
            }
        }

        monkeys.add(current);
        return monkeys;
    }

    private static int parseSendToTruthy(String line) {
        String[] parts = line.split("monkey");
        return Integer.parseInt(parts[1].trim());
    }

    private static int parseSendToFalsy(String line) {
        String[] parts = line.split("monkey");
        return Integer.parseInt(parts[1].trim());
    }

    private static int parseDivisibleBy(String line) {
        String[] parts = line.split("divisible by");
        return Integer.parseInt(parts[1].trim());
    }

    private static Function<Integer, Integer> parseOperationLine(String line) {
        String[] parts = line.split("=");
        String[] expression = parts[1].trim().split(" ");
        boolean isOld = expression[2].equals("old");
        switch (expression[1]) {
            // only + and * are supported...
            case "*":
                return old -> {
                    int value = isOld ? old : Integer.parseInt(expression[2]);
                    return old * value;
                };
            case "+":
                return old -> {
                    int value = isOld ? old : Integer.parseInt(expression[2]);
                    return old + value;
                };
            default:
                // unsupported op...
        }

        return null;
    }

    private static List<Integer> parseItemsLine(String line) {
        List<String> items = Arrays.asList(line.split("Starting items:")[1].split(","));
        return items.stream().map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
    }

    private static Monkey parseMonkeyLine(String part) {
        Monkey current;
        String number = part;
        int monkey = Integer.parseInt(number.substring(0, number.length() - 1));
        current = new Monkey(monkey);
        return current;
    }
}
