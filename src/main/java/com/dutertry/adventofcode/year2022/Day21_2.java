package com.dutertry.adventofcode.year2022;

import com.dutertry.adventofcode.Rational;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21_2 {
    private static final Pattern VALUE_PATTERN = Pattern.compile(
            "([a-z]+): ([\\-0-9]+)");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "([a-z]+): ([a-z]+) ([+\\-*/]) ([a-z]+)");
    private static record Expression(String left, String operator, String right) {}

    private static class Polynomial {
        Map<Integer, Rational> coefficients;

        public Polynomial(Map<Integer, Rational> coefficients) {
            this.coefficients = new TreeMap<>(coefficients);
        }

        public Polynomial(long value) {
            this.coefficients = Collections.singletonMap(0, new Rational(new BigInteger(String.valueOf(value)), BigInteger.ONE));
        }

        public Polynomial add(Polynomial other) {
            Map<Integer, Rational> newCoefficients = new HashMap<>(coefficients);
            for (Map.Entry<Integer, Rational> entry : other.coefficients.entrySet()) {
                int key = entry.getKey();
                Rational value = entry.getValue();
                Rational newValue = newCoefficients.getOrDefault(key, Rational.ZERO).plus(value);
                if(newValue.equals(Rational.ZERO)) {
                    newCoefficients.remove(key);
                } else {
                    newCoefficients.put(key, newValue);
                }
            }
            return new Polynomial(newCoefficients);
        }

        public Polynomial subtract(Polynomial other) {
            Map<Integer, Rational> newCoefficients = new HashMap<>(coefficients);
            for (Map.Entry<Integer, Rational> entry : other.coefficients.entrySet()) {
                int key = entry.getKey();
                Rational value = entry.getValue();
                Rational newValue = newCoefficients.getOrDefault(key, Rational.ZERO).minus(value);
                if(newValue.equals(Rational.ZERO)) {
                    newCoefficients.remove(key);
                } else {
                    newCoefficients.put(key, newValue);
                }
            }
            return new Polynomial(newCoefficients);
        }

        public Polynomial multiply(Polynomial other) {
            Map<Integer, Rational> newCoefficients = new HashMap<>();
            for (Map.Entry<Integer, Rational> entry1 : coefficients.entrySet()) {
                int key1 = entry1.getKey();
                Rational value1 = entry1.getValue();
                for (Map.Entry<Integer, Rational> entry2 : other.coefficients.entrySet()) {
                    int key2 = entry2.getKey();
                    Rational value2 = entry2.getValue();
                    int key = key1 + key2;
                    Rational value = value1.times(value2);
                    Rational newValue = newCoefficients.getOrDefault(key, Rational.ZERO).plus(value);
                    if(newValue.equals(Rational.ZERO)) {
                        newCoefficients.remove(key);
                    } else {
                        newCoefficients.put(key, newValue);
                    }
                }
            }
            return new Polynomial(newCoefficients);
        }

        public Polynomial divideBy(Polynomial other) {
            if(other.coefficients.size() > 1 || !other.coefficients.containsKey(0)) {
                throw new RuntimeException("Invalid divisor: " + other);
            }
            Rational divisor = other.coefficients.get(0);
            Map<Integer, Rational> newCoefficients = new HashMap<>();
            for (Map.Entry<Integer, Rational> entry : coefficients.entrySet()) {
                int key = entry.getKey();
                Rational value = entry.getValue();
                Rational newValue = value.divides(divisor);
                newCoefficients.put(key, newValue);
            }
            return new Polynomial(newCoefficients);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<Integer, Rational> entry : coefficients.entrySet()) {
                int key = entry.getKey();
                Rational value = entry.getValue();
                if(!builder.isEmpty()) {
                    builder.append(" + ");
                }
                if(key == 0) {
                    builder.append(value);
                } else if(value.equals(Rational.ONE)) {
                    builder.append("x");
                } else {
                    builder.append(value);
                    builder.append("x");
                }
                if(key > 1) {
                    builder.append("^");
                    builder.append(key);
                }
            }
            return builder.toString();
        }
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(21)) {
            Map<String, Expression> expressionMap = new HashMap<>();
            Map<String, Polynomial> polynomialMap = new HashMap<>();

            String line;
            int index = 0;
            while((line = br.readLine()) != null) {
                Matcher matcher = VALUE_PATTERN.matcher(line);
                if (matcher.find()) {
                    String monkey = matcher.group(1);
                    long value = Long.parseLong(matcher.group(2));
                    polynomialMap.put(monkey, new Polynomial(value));
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

            polynomialMap.put("humn", new Polynomial(Collections.singletonMap(1, Rational.ONE)));

            main: while(true) {
                Iterator<Map.Entry<String, Expression>> iterator = expressionMap.entrySet().iterator();
                while(iterator.hasNext()) {
                    Map.Entry<String, Expression> entry = iterator.next();
                    String monkey = entry.getKey();
                    Expression expression = entry.getValue();
                    if (canEvaluate(expression, polynomialMap)) {
                        if(monkey.equals("root")) {
                            Rational a0 = polynomialMap.get(expression.left).coefficients.getOrDefault(0, Rational.ZERO);
                            Rational a1 = polynomialMap.get(expression.left).coefficients.getOrDefault(1, Rational.ZERO);
                            Rational b0 = polynomialMap.get(expression.right).coefficients.getOrDefault(0, Rational.ZERO);
                            Rational b1 = polynomialMap.get(expression.right).coefficients.getOrDefault(1, Rational.ZERO);

                            Rational x = b0.minus(a0).divides(a1.minus(b1));
                            System.out.println(x);

                            break main;
                        }

                        Polynomial value = evaluate(expression, polynomialMap);

                        polynomialMap.put(monkey, value);
                        iterator.remove();
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean canEvaluate(Expression expression, Map<String, Polynomial> polynomialMap) {
        return polynomialMap.containsKey(expression.left) && polynomialMap.containsKey(expression.right);
    }

    private static Polynomial evaluate(Expression expression, Map<String, Polynomial> polynomialMap) {
        Polynomial left = polynomialMap.get(expression.left);
        Polynomial right = polynomialMap.get(expression.right);
        return switch (expression.operator) {
            case "+" -> left.add(right);
            case "-" -> left.subtract(right);
            case "*" -> left.multiply(right);
            case "/" -> left.divideBy(right);
            default -> throw new RuntimeException("Invalid operator: " + expression.operator);
        };
    }
}
