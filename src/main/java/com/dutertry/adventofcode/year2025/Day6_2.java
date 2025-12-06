package com.dutertry.adventofcode.year2025;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day6_2 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(6);
            lines = rotate(lines);
            long total = 0;
            long current = 0;
            String operation = "+";
            for (String line : lines) {
                if(StringUtils.isBlank(line)) {
                    total += current;
                    current = 0;
                    continue;
                }

                long number = Long.parseLong(line.substring(0, line.length()-1).trim());
                if(current == 0) {
                    operation = line.substring(line.length()-1);
                    current = number;
                } else {
                    if(operation.equals("+")) {
                        current += number;
                    } else {
                        current *= number;
                    }
                }
            }
            if(current != 0) {
                total += current;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> rotate(List<String> lines) {
        List<StringBuilder> result = new LinkedList<>();

        for(String line : lines) {
            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if(result.size() <= i) {
                    result.add(new StringBuilder());
                }
                result.get(i).append(c);
            }
        }

        return result.stream().map(StringBuilder::toString).toList();
    }
}
