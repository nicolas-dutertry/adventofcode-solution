package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class Day11_2 {
    private static class Item {
        private Map<Integer, Integer> mods = new HashMap<>();

        public void add(int i) {
            for(Map.Entry<Integer, Integer> entry : mods.entrySet()) {
                int div = entry.getKey();
                int mod = entry.getValue();
                int newmod = (mod + i) % div;
                entry.setValue(newmod);
            }
        }

        public void multiply(int i) {
            for(Map.Entry<Integer, Integer> entry : mods.entrySet()) {
                int div = entry.getKey();
                int mod = entry.getValue();
                int newmod = (mod * i) % div;
                entry.setValue(newmod);
            }
        }

        public void square() {
            for(Map.Entry<Integer, Integer> entry : mods.entrySet()) {
                int div = entry.getKey();
                int mod = entry.getValue();
                int newmod = (mod*mod) % div;
                entry.setValue(newmod);
            }
        }

        public boolean isDivisibleBy(int i) {
            int mod = mods.get(i);
            return mod == 0;
        }
    }

    private static class Monkey {
        private List<Integer> initialValues;
        private List<Item> items;
        private String operation;
        private int divisibleBy;
        private int trueMonkey;
        private int falseMonkey;
        private long inspectCount = 0;

        void initItems(List<Integer> divisors) {
            items = new LinkedList<>();
            for(int i : initialValues) {
                Item item = new Item();
                for(int div : divisors) {
                    item.mods.put(div, i % div);
                }
                items.add(item);
            }
        }

        void playRound() {
            for (Item item : items) {
                inspectCount++;
                item = evaluate(operation, item);
                Monkey nextMonkey;
                if (item.isDivisibleBy(divisibleBy)) {
                    nextMonkey = monkeys.get(trueMonkey);
                } else {
                    nextMonkey = monkeys.get(falseMonkey);
                }
                nextMonkey.items.add(item);
            }
            items.clear();
        }

    }

    private static List<Monkey> monkeys = new LinkedList<>();

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(11)) {
            String line;
            List<Integer> divisors = new LinkedList<>();
            while((line = br.readLine()) != null) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }

                Monkey monkey = new Monkey();
                monkeys.add(monkey);

                line = br.readLine();
                String[] items = StringUtils.split(line.substring(18), ",");
                monkey.initialValues = Arrays.stream(items)
                        .map(StringUtils::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                line = br.readLine();
                monkey.operation = line.substring(19);

                line = br.readLine();
                monkey.divisibleBy = Integer.parseInt(line.substring(21));
                divisors.add(monkey.divisibleBy);

                line = br.readLine();
                monkey.trueMonkey = Integer.parseInt(line.substring(29));

                line = br.readLine();
                monkey.falseMonkey = Integer.parseInt(line.substring(30));
            }

            for(Monkey monkey : monkeys) {
                monkey.initItems(divisors);
            }

            for(int round = 0; round < 10000; round++) {
                for(Monkey monkey : monkeys) {
                    monkey.playRound();
                }
            }

            monkeys.sort((m1, m2) ->  {
                long dif = m2.inspectCount - m1.inspectCount;
                if(dif == 0) {
                    return 0;
                } else {
                    return dif > 0 ? 1 : -1;
                }
            });

            long total = monkeys.get(0).inspectCount * monkeys.get(1).inspectCount;
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Item evaluate(String operation, Item old) {
        String[] parts = StringUtils.split(operation, " ");
        String operator = parts[1];
        String s = parts[2];
        if(s.equals("old") && operator.equals("*")) {
            old.square();
        } else {
            int i = Integer.parseInt(s);
            switch (operator) {
                case "+" :
                    old.add(i);
                    break;
                case "*" :
                    old.multiply(i);
                    break;
                default :
                    throw new RuntimeException("Oups !");
            };
        }
        return old;
    }
}
