package com.dutertry.adventofcode.year2023;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Day15_1 {
    public static void main(String[] args) {
        try {
            String s = AdventUtils.getString(15);
            String[] array = StringUtils.split(s, ",");
            long total = 0;
            for (String step : array) {
                total+= getHash(step);
            }
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int getHash(String s) {
        int result = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result += (int)c;
            result = result*17;
            result = result % 256;
        }
        return result;
    }
}
