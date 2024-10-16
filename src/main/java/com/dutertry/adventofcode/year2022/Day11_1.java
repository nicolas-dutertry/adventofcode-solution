package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11_1 {
    private static List<Monkey> monkeys = new LinkedList<>();

    private static class Monkey {
        private List<Integer> items;
        private String operation;
        private int divisibleBy;
        private int trueMonkey;
        private int falseMonkey;
        private int inspectCount = 0;

        void playRound() {
            for (int item : items) {
                inspectCount++;
                item = evaluate(operation, item);
                item = item / 3;
                Monkey nextMonkey;
                if (item % divisibleBy == 0) {
                    nextMonkey = monkeys.get(trueMonkey);
                } else {
                    nextMonkey = monkeys.get(falseMonkey);
                }
                nextMonkey.items.add(item);
            }
            items.clear();
        }

    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(11, true)) {
            String line;
            while((line = br.readLine()) != null) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }

                Monkey monkey = new Monkey();
                monkeys.add(monkey);

                line = br.readLine();
                String[] items = StringUtils.split(line.substring(18), ",");
                monkey.items = Arrays.stream(items)
                        .map(StringUtils::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                line = br.readLine();
                monkey.operation = line.substring(19);

                line = br.readLine();
                monkey.divisibleBy = Integer.parseInt(line.substring(21));

                line = br.readLine();
                monkey.trueMonkey = Integer.parseInt(line.substring(29));

                line = br.readLine();
                monkey.falseMonkey = Integer.parseInt(line.substring(30));
            }

            for(int round = 0; round < 20; round++) {
                for(Monkey monkey : monkeys) {
                    monkey.playRound();
                }
            }

            monkeys.sort((m1, m2) -> m2.inspectCount - m1.inspectCount);
            int total = monkeys.get(0).inspectCount * monkeys.get(1).inspectCount;
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int evaluate(String operation, int old) {
        String s = StringUtils.replace(operation, "old", Integer.toString(old));
        String[] parts = StringUtils.split(s, " ");
        int a = Integer.parseInt(parts[0]);
        String operator = parts[1];
        int b = Integer.parseInt(parts[2]);
        return switch(operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> throw new RuntimeException("Oups !");
        };
    }
}
