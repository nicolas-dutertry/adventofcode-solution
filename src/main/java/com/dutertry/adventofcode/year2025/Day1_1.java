package com.dutertry.adventofcode.year2025;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day1_1 {
    int MAX_POS = 99;

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            int pos = 50;
            int zeroCount = 0;
            while((line = br.readLine()) != null ) {
                boolean left = line.charAt(0) == 'L';
                int steps = Integer.parseInt(line.substring(1));
                pos = getNextPosition(pos, left, steps);
                if(pos == 0) {
                    zeroCount++;
                }
            }
            System.out.println(zeroCount);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNextPosition(int pos, boolean left, int steps) {
        int newPos;
        if(left) {
            newPos = pos - steps;
        } else  {
            newPos = pos + steps;
        }
        return Math.floorMod(newPos, 100);
    }

}
