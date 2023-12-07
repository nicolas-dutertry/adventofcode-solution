package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Day1_1 {

    public static void main(String[] args) {
        int total = 0;
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            while((line = br.readLine()) != null ) {
                int firstIdx = StringUtils.indexOfAny(line, "0","1","2","3","4","5","6","7","8","9");
                int lastIdx = StringUtils.lastIndexOfAny(line, "0","1","2","3","4","5","6","7","8","9");
                int i = 10*getValue(line, firstIdx) + getValue(line, lastIdx);
                total += i;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println(total);
    }
    
    private static int getValue(String line, int index) {
        return Integer.valueOf(line.substring(index, index+1));
    }

}
