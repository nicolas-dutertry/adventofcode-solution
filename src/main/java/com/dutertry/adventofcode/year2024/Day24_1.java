package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24_1 {
    private static final Pattern PATTERN = Pattern.compile("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)");

    private record Gate(String input1, String input2, String operator, String output) {
        public boolean canProcess(Map<String, Integer> wireMap) {
            return wireMap.containsKey(input1) && wireMap.containsKey(input2) && !wireMap.containsKey(output);
        }

        public void process(Map<String, Integer> wireMap) {
            Integer value1 = wireMap.get(input1);
            Integer value2 = wireMap.get(input2);

            switch(operator) {
                case "AND" -> wireMap.put(output, value1 & value2);
                case "OR" -> wireMap.put(output, value1 | value2);
                case "XOR" -> wireMap.put(output, value1 ^ value2);
            }
        }
    }

    public static void main(String[] args) {
        Map <String, Integer> valuesMap = new HashMap<>();
        List<Gate> gates = new ArrayList<>();

        try(BufferedReader br = AdventUtils.getBufferedReader(24)) {
            long total = 0;

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
                    int value = Integer.parseInt(parts[1]);
                    valuesMap.put(wire, value);
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

            List<Gate> toDoGates = new ArrayList<>(gates);
            while(!toDoGates.isEmpty()) {
                Iterator<Gate> iterator = toDoGates.iterator();
                while(iterator.hasNext()) {
                    Gate gate = iterator.next();
                    if(gate.canProcess(valuesMap)) {
                        gate.process(valuesMap);
                        iterator.remove();
                    }
                }
            }

            String z = binValue("z", valuesMap);
            System.out.println(Long.parseLong(z, 2));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static String binValue(String s, Map <String, Integer> valueMap) {
        List<String> zInputs = valueMap.keySet().stream().filter(wire -> wire.startsWith(s)).sorted(Comparator.reverseOrder()).toList();
        StringBuilder sb = new StringBuilder();
        for(String zInput : zInputs) {
            sb.append(valueMap.get(zInput));
        }
        return sb.toString();
    }
}
