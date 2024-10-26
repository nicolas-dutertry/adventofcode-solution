package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21_1 {
    private static final Pattern VALUE_PATTERN = Pattern.compile(
            "([a-z]+): ([\\-0-9]+)");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "([a-z]+): ([a-z]+) ([+\\-*/]) ([a-z]+)");
    private static record Expression(String left, String operator, String right) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(21)) {
            Map<String, Expression> expressionMap = new HashMap<>();
            Map<String, Long> valueMap = new HashMap<>();

            String line;
            int index = 0;
            while((line = br.readLine()) != null) {
                Matcher matcher = VALUE_PATTERN.matcher(line);
                if (matcher.find()) {
                    String monkey = matcher.group(1);
                    long value = Long.parseLong(matcher.group(2));
                    valueMap.put(monkey, value);
                } else {
                    matcher = EXPRESSION_PATTERN.matcher(line);
                    if (matcher.find()) {
                        String monkey = matcher.group(1);
                        String left = matcher.group(2);
                        String operator = matcher.group(3);
                        String right = matcher.group(4);
                        expressionMap.put(monkey, new Expression(left, operator, right));
                    } else {
                        throw new RuntimeException("Invalid input: " + line);
                    }
                }
            }

            main: while(true) {
                Iterator<Map.Entry<String, Expression>> iterator = expressionMap.entrySet().iterator();
                while(iterator.hasNext()) {
                    Map.Entry<String, Expression> entry = iterator.next();
                    String monkey = entry.getKey();
                    Expression expression = entry.getValue();
                    if (canEvaluate(expression, valueMap)) {
                        long value = evaluate(expression, valueMap);

                        if(monkey.equals("root")) {
                            System.out.println(value);
                            break main;
                        }

                        valueMap.put(monkey, value);
                        iterator.remove();
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean canEvaluate(Expression expression, Map<String, Long> valueMap) {
        return valueMap.containsKey(expression.left) && valueMap.containsKey(expression.right);
    }

    private static long evaluate(Expression expression, Map<String, Long> valueMap) {
        long left = valueMap.get(expression.left);
        long right = valueMap.get(expression.right);
        return switch (expression.operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> throw new RuntimeException("Invalid operator: " + expression.operator);
        };
    }
}
