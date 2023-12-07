package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Day1_2 {

    public static void main(String[] args) {
        int total = 0;
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            while((line = br.readLine()) != null ) {
                int firstIdx = StringUtils.indexOfAny(line, "0","1","2","3","4","5","6","7","8","9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
                int lastIdx = StringUtils.lastIndexOfAny(line, "0","1","2","3","4","5","6","7","8","9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
                int i = 10*getValue(line, firstIdx) + getValue(line, lastIdx);
                System.out.println(line + " : " + i);
                total += i;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println(total);
    }
    
    private static int getValue(String line, int index) {
        String sub = line.substring(index);
        if(StringUtils.startsWithAny(sub, "0","1","2","3","4","5","6","7","8","9")) {
            return Integer.valueOf(line.substring(index, index+1));
        }
        if(sub.startsWith("one")) {
            return 1;
        }
        if(sub.startsWith("two")) {
            return 2;
        }
        if(sub.startsWith("three")) {
            return 3;
        }
        if(sub.startsWith("four")) {
            return 4;
        }
        if(sub.startsWith("five")) {
            return 5;
        }
        if(sub.startsWith("six")) {
            return 6;
        }
        if(sub.startsWith("seven")) {
            return 7;
        }
        if(sub.startsWith("eight")) {
            return 8;
        }
        if(sub.startsWith("nine")) {
            return 9;
        }
        
        throw new RuntimeException("oups");
    }

}
