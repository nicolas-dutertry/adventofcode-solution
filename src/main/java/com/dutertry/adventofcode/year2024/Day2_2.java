package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day2_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
            String line;
            int safeCount = 0;
            main: while((line = br.readLine()) != null ) {
                List<Integer> levels = Arrays.stream(StringUtils.split(line)).map(Integer::parseInt).toList();
                if(isAlmostSafe(levels)) {
                    safeCount++;
                }
            }
            System.out.println(safeCount);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isAlmostSafe(List<Integer> levels) {
        if(isSafe(levels)) {
            return true;
        }

        for (int i = 0; i < levels.size(); i++) {
            List<Integer> newLevels = new LinkedList<>(levels);
            newLevels.remove(i);
            if(isSafe(newLevels)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSafe(List<Integer> levels) {
        boolean increase = levels.get(1) > levels.get(0);
        int diff = Math.abs(levels.get(1) - levels.get(0));
        if(diff < 1 || diff > 3) {
            return false;
        }
        for (int i = 2; i < levels.size(); i++) {
            if(increase && levels.get(i) < levels.get(i-1)) {
                return false;
            }
            if(!increase && levels.get(i) > levels.get(i-1)) {
                return false;
            }
            diff = Math.abs(levels.get(i) - levels.get(i-1));
            if(diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }

}
