package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5_2 {
    private static class Rule {
        public int before;
        public int after;

        public Rule(int before, int after) {
            this.before = before;
            this.after = after;
        }
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
            String line;
            List<Rule> rules = new LinkedList<>();
            List<List<Integer>> updates = new LinkedList<>();
            boolean ruleSection = true;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    ruleSection = false;
                    continue;
                }

                if(ruleSection) {
                    String[] parts = StringUtils.split(line, "|");
                    int before = Integer.parseInt(parts[0]);
                    int after = Integer.parseInt(parts[1]);
                    rules.add(new Rule(before, after));
                } else {
                    List<Integer> parts = Arrays.stream(StringUtils.split(line, ",")).map(Integer::parseInt).collect(Collectors.toList());
                    updates.add(parts);
                }
            }

            int total = 0;
            for(List<Integer> update : updates) {
                boolean correct = true;
                for(int i = 0; i < update.size(); i++) {
                    int value = update.get(i);
                    List<Integer> previousValues = update.subList(0, i);

                    List<Integer> afters = new LinkedList<>();

                    for(int previousValue : previousValues) {
                        for(Rule rule : rules) {
                            if(rule.before == value && rule.after == previousValue) {
                                afters.add(previousValue);
                                break;
                            }
                        }
                    }

                    if(!afters.isEmpty()) {
                        correct = false;
                        update.removeAll(afters);
                        i = update.indexOf(value);
                        update.addAll(i+1, afters);
                    }
                }

                if(correct) {
                    continue;
                }

                int middle = update.get(update.size() / 2);
                total += middle;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
