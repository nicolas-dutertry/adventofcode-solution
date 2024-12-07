package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Day7_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(7)) {
            long sum = 0;
            List<BiFunction<Long, Long, Long>> operators = List.of(
                    Long::sum,
                    (a, b) -> a * b,
                    (a, b) -> {
                        int digitCount = String.valueOf(b).length();
                        for(int i = 0; i < digitCount; i++) {
                            a *= 10;
                        }
                        return a + b;
                    }
            );
            String line;
            while((line = br.readLine()) != null ) {
                String[] array = StringUtils.split(line, ":");
                long result = Long.parseLong(array[0]);
                String[] array2 = StringUtils.split(array[1]);
                List<Long> values = Arrays.stream(array2).map(Long::parseLong).toList();

                Set<Long> possibleValues = new HashSet<>();
                possibleValues.add(values.get(0));

                for(int i = 1; i < values.size(); i++) {
                    Set<Long> newPossibleValues = new HashSet<>();
                    for(Long possibleValue : possibleValues) {
                        for (BiFunction<Long, Long, Long> operator : operators) {
                            long newPossible = operator.apply(possibleValue, values.get(i));
                            if(newPossible <= result) {
                                newPossibleValues.add(newPossible);
                            }
                        }
                    }
                    possibleValues = newPossibleValues;
                }
                if(possibleValues.contains(result)) {
                    sum += result;
                }
            }

            System.out.println(sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
