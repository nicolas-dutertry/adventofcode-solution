package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day10_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(10)) {
            int cycle = 1;
            int X = 1;
            int nextSignalCycle = 20;
            int total = 0;
            String line;
            while((line = br.readLine()) != null) {
                cycle++;
                if(cycle == nextSignalCycle) {
                    total += X*cycle;
                    nextSignalCycle += 40;
                }
                if(line.startsWith("addx")) {
                    int value = Integer.parseInt(line.substring(5));
                    cycle++;
                    X += value;
                    if(cycle == nextSignalCycle) {
                        total += X*cycle;
                        nextSignalCycle += 40;
                    }
                } else if(!line.equals("noop")) {
                    throw new RuntimeException("Oups !");
                }

                if(nextSignalCycle > 220) {
                    break;
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
