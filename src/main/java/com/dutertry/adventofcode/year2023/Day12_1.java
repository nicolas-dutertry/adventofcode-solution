package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day12_1 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(12);
            int total = 0;
            for (String line : lines) {
                String[] array = StringUtils.split(line);
                List<Integer> blocks = Arrays.asList(StringUtils.split(array[1], ","))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                String s = array[0];
                
                int dCount = StringUtils.countMatches(s, '#');
                int qCount = StringUtils.countMatches(s, '?');
                
                int totalcount = blocks.stream().mapToInt(i->i).sum();
                int missingCount = totalcount-dCount;
                
                long max = Long.parseLong(StringUtils.repeat('1', qCount), 2);
                int count = 0;
                for(long l = 0; l <= max; l++) {
                    String ls = StringUtils.leftPad(Long.toBinaryString(l), qCount, '0');
                    if(StringUtils.countMatches(ls, '1') != missingCount) {
                        continue;
                    }
                    int idx = 0;
                    StringBuilder builder = new StringBuilder(s);
                    for(int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if(c == '?') {
                            if(ls.charAt(idx) == '1') {
                                builder.setCharAt(i, '#');
                            } else {
                                builder.setCharAt(i, '.');
                            }
                            idx++;
                        }
                    }
                    if(countBlocks(builder).equals(blocks)) {
                        count++;
                    }
                }
                total += count;
            }
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<Integer> countBlocks(CharSequence line) {
        List<Integer> blocks = new LinkedList<>();
        int current = 0;
        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if(c == '#') {
                current++;
            } else {
                if(current > 0) {
                    blocks.add(current);
                    current = 0;
                }
            }
        }
        if(current > 0) {
            blocks.add(current);
            current = 0;
        }
        return blocks;
    }

}
