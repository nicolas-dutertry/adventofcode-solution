package com.dutertry.adventofcode.year2025;

import java.io.BufferedReader;
import java.io.IOException;

public class Day1_2 {
    int MAX_POS = 99;

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            int pos = 50;
            int zeroCount = 0;
            while((line = br.readLine()) != null ) {
                boolean left = line.charAt(0) == 'L';
                int steps = Integer.parseInt(line.substring(1));
                zeroCount += countZero(pos, left, steps);
                pos = getNextPosition(pos, left, steps);

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

    private static int countZero(int pos, boolean left, int steps) {
        int nextpos = pos;
        int zeroCount = 0;
        for(int i = 0; i < steps; i++) {
            if(!left) {
                nextpos = nextpos + 1;
            }
            else {
                nextpos = nextpos - 1;
            }
            if(nextpos < 0) {
                nextpos = 99;
            }
            if(nextpos > 99) {
                nextpos = 0;
            }
            if(nextpos == 0) {
                // count zero
                zeroCount++;
            }
        }
        return zeroCount;
    }

}
