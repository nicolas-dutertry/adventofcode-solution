package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day13_2 {
    public static void main(String[] args) {
        List<List<Object>> packets = new LinkedList<>();
        List<Object> divider1 = parseList("[[2]]");
        List<Object> divider2 = parseList("[[6]]");
        packets.add(divider1);
        packets.add(divider2);
        try(BufferedReader br = AdventUtils.getBufferedReader(13)) {
            String line;
            while((line = br.readLine()) != null) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }
                packets.add(parseList(line));
            }
            packets.sort(Day13_2::compare);
            int i1 = packets.indexOf(divider1) + 1;
            int i2 = packets.indexOf(divider2) + 1;
            System.out.println(i1 * i2);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object> parseList(String line) {
        LinkedList<List<Object>> lists = new LinkedList<>();
        lists.add(new LinkedList<>());
        String currentString = "";
        for(int i = 1; i < line.length()-1; i++) {
            char c = line.charAt(i);
            if(c == '[') {
                List<Object> newList = new LinkedList<>();
                lists.getLast().add(newList);
                lists.add(newList);
            } else if(c == ']') {
                if(!currentString.isEmpty()) {
                    lists.getLast().add(Integer.parseInt(currentString));
                    currentString = "";
                }
                lists.removeLast();
            } else if (c == ',') {
                if(!currentString.isEmpty()) {
                    lists.getLast().add(Integer.parseInt(currentString));
                    currentString = "";
                }
            } else {
                currentString += c;
            }
        }
        if(!currentString.isEmpty()) {
            lists.getLast().add(Integer.parseInt(currentString));
            currentString = "";
        }

        return lists.get(0);
    }

    private static int compare(List<?> left, List<?> right) {
        for(int i = 0; i < left.size(); i++) {
            if(right.size() < i+1) {
                return 1;
            }

            Object leftObj = left.get(i);
            Object rightObj = right.get(i);

            if(leftObj instanceof Integer && rightObj instanceof Integer) {
                int leftInt = (Integer) leftObj;
                int rightInt = (Integer) rightObj;
                int comparison = Integer.compare(leftInt, rightInt);
                if(comparison != 0) {
                    return comparison;
                };
            } else {
                List<Object> leftList;
                if(leftObj instanceof Integer) {
                    leftList = Collections.singletonList(leftObj);
                } else {
                    leftList = (List<Object>) leftObj;
                }

                List<Object> rightList;
                if(rightObj instanceof Integer) {
                    rightList = Collections.singletonList(rightObj);
                } else {
                    rightList = (List<Object>) rightObj;
                }
                int comparison = compare(leftList, rightList);
                if(comparison != 0) {
                    return comparison;
                }
            }

        }

        if(left.size() < right.size()) {
            return -1;
        } else {
            return 0;
        }
    }
}
