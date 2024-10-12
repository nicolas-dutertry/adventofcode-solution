package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5_2 {
    private static final Pattern PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
            Map<Integer, List<String>> stacks = new HashMap<>();
            String line;
            boolean stacksReady = false;
            while((line = br.readLine()) != null) {
                if(!stacksReady) {
                    if(line.startsWith(" 1")) {
                        stacksReady = true;
                        continue;
                    }
                    line = line + " ";
                    int stackCount = line.length() / 4;
                    for(int i = 0; i < stackCount; i++) {
                        int start = i * 4 + 1;
                        int end = start + 1;
                        String letter = line.substring(start, end);
                        if(StringUtils.isNotBlank(letter)) {
                            List<String> stack = stacks.get(i+1);
                            if (stack == null) {
                                stack = new LinkedList<>();
                                stacks.put(i+1, stack);
                            }
                            stack.add(0, letter);
                        }
                    }
                } else {
                    if(StringUtils.isBlank(line)) {
                        continue;
                    }
                    Matcher matcher = PATTERN.matcher(line);
                    matcher.find();
                    int count = Integer.parseInt(matcher.group(1));
                    int from = Integer.parseInt(matcher.group(2));
                    int to = Integer.parseInt(matcher.group(3));

                    List<String> fromStack = stacks.get(from);
                    List<String> toStack = stacks.get(to);
                    int fromIndex = fromStack.size() - count;
                    for(int i = 0; i < count; i++) {
                        String letter = fromStack.remove(fromIndex);
                        toStack.add(letter);
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            for(int i = 1; i <= stacks.size(); i++) {
                List<String> stack = stacks.get(i);
                result.append(stack.get(stack.size() - 1));
            }
            System.out.println(result);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
