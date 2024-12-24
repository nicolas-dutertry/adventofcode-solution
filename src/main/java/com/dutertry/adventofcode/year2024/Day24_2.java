package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.Pair;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24_2 {
    private static final Pattern PATTERN = Pattern.compile("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)");

    private record Gate(String input1, String input2, String operator, String output) {
        public boolean canProcess(Map<String, Expression> expressionMap) {
            return expressionMap.containsKey(input1) && expressionMap.containsKey(input2) && !expressionMap.containsKey(output);
        }

        public void process(Map<String, Expression> expressionMap) {
            Expression value1 = expressionMap.get(input1);
            Expression value2 = expressionMap.get(input2);

            expressionMap.put(output, new GateExpression(operator, new Pair<>(value1, value2)));
        }
    }

    private interface Expression {
        int eval(Map<String, Integer> valuesMap);
    }

    private record SimpleExpression(String input) implements Expression {
        public String toString() {
            return input;
        }

        public int eval(Map<String, Integer> values) {
            return values.get(input);
        }
    }

    private record GateExpression(String operator, Pair<Expression> inputs) implements Expression {
        public String toString() {
            return operator +
                    "(" +
                    inputs.getFirst() +
                    "," +
                    inputs.getSecond() +
                    ")";
        }

        public int eval(Map<String, Integer> valuesMap) {
            Expression expression1 = inputs.getFirst();
            Expression expression2 = inputs.getSecond();

            int value1 = expression1.eval(valuesMap);
            int value2 = expression2.eval(valuesMap);
            return switch (operator) {
                case "AND" -> value1 & value2;
                case "OR" -> value1 | value2;
                case "XOR" -> value1 ^ value2;
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        }
    }

    public static void main(String[] args) {
        Map <String, Expression> expressionMap = new HashMap<>();
        List<Gate> gates = new ArrayList<>();

        try(BufferedReader br = AdventUtils.getBufferedReader(24)) {
            String line;
            boolean iswire = true;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    iswire = false;
                    continue;
                }

                if(iswire) {
                    String[] parts = StringUtils.split(line, ": ");
                    String wire = parts[0];
                    expressionMap.put(wire, new SimpleExpression(wire));
                } else {
                    Matcher matcher = PATTERN.matcher(line);
                    matcher.matches();
                    String input1 = matcher.group(1);
                    String operator = matcher.group(2);
                    String input2 = matcher.group(3);
                    String output = matcher.group(4);
                    Gate gate = new Gate(input1, input2, operator, output);
                    gates.add(gate);
                }
            }

            Map<String, Expression> wireMapInit = new HashMap<>(expressionMap);
            process(gates, expressionMap);

            Map<String, Expression> expectedExpressionMap = buildExpected();

            int errorIndex = getErrorIndex(expressionMap, expectedExpressionMap);

            Set<String> outputs = new HashSet<>();
            for(int step = 0; step < 4; step++) {
                main: for (int i = 0; i < gates.size(); i++) {
                    for (int j = i; j < gates.size(); j++) {
                        List<Gate> newGates = splitGateOutputs(gates, i, j);
                        Map<String, Expression> newWireMap = new HashMap<>(wireMapInit);
                        if (process(newGates, newWireMap)) {
                            int newErrorIndex = getErrorIndex(newWireMap, expectedExpressionMap);
                            if (newErrorIndex > errorIndex) {
                                errorIndex = newErrorIndex;
                                gates = newGates;
                                outputs.add(gates.get(i).output);
                                outputs.add(gates.get(j).output);
                                break main;
                            }

                            if(newErrorIndex == -1) {
                                outputs.add(gates.get(i).output);
                                outputs.add(gates.get(j).output);

                                System.out.println(outputs.stream().sorted().collect(Collectors.joining(",")));
                                return;
                            }
                        }
                    }
                }
            }

            System.out.println(outputs.stream().sorted().collect(Collectors.joining(",")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Gate> splitGateOutputs(List<Gate> gates, int i1, int i2) {
        List<Gate> newGates = new ArrayList<>(gates);
        Gate gate1 = newGates.get(i1);
        Gate gate2 = newGates.get(i2);

        Gate newGate1 = new Gate(gate1.input1, gate1.input2, gate1.operator, gate2.output);
        Gate newGate2 = new Gate(gate2.input1, gate2.input2, gate2.operator, gate1.output);

        newGates.set(i1, newGate1);
        newGates.set(i2, newGate2);

        return newGates;
    }


    private static int getErrorIndex(Map<String, Expression> wireMap, Map<String, Expression> expected) {
        for(int i = 0; i < 45; i++) {
            String key = "z" + StringUtils.leftPad(String.valueOf(i), 2, "0");
            Expression z = wireMap.get(key);
            Expression e = expected.get(key);
            if(!z.equals(e)) {
                return i;
            }
        }

        return -1;
    }

    private static boolean process(List<Gate> gates, Map<String, Expression> wireMap) {
        List<Gate> toDoGates = new ArrayList<>(gates);
        while(true) {
            int toDoCount = toDoGates.size();
            Iterator<Gate> iterator = toDoGates.iterator();
            while(iterator.hasNext()) {
                Gate gate = iterator.next();
                if(gate.canProcess(wireMap)) {
                    gate.process(wireMap);
                    iterator.remove();
                }
            }

            if(toDoGates.isEmpty()) {
                return true;
            } else if(toDoCount == toDoGates.size()) {
                return false;
            }
        }
    }

    private static Map<String, Expression> buildExpected() {
        Expression carry = null;
        Map<String, Expression> expected = new HashMap<>();
        for(int i = 0; i < 45; i++) {
            String key = StringUtils.leftPad(String.valueOf(i), 2, "0");
            Expression z = new GateExpression(
                    "XOR",
                    new Pair<>(
                            new SimpleExpression("x" + key),
                            new SimpleExpression("y" + key)));
            if(carry != null) {
                z = new GateExpression(
                        "XOR",
                        new Pair<>(z, carry));
            }

            Expression newCarry = new GateExpression(
                    "AND",
                    new Pair<>(new SimpleExpression("x" + key), new SimpleExpression("y" + key)));
            if(carry != null) {
                newCarry = new GateExpression(
                        "OR",
                        new Pair<>(
                            newCarry,
                            new GateExpression(
                                    "AND",
                                    new Pair<>(
                                            new GateExpression(
                                                    "XOR",
                                                    new Pair<>(
                                                            new SimpleExpression("x" + key),
                                                            new SimpleExpression("y" + key))),
                                            carry
                                    )
                            )
                        )
                );
            }
            carry = newCarry;

            expected.put("z" + key, z);
        }
        expected.put("z45", carry);
        return expected;
    }
}
