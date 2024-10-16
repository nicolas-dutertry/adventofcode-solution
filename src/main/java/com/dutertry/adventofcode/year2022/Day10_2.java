package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;

public class Day10_2 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(10)) {
            int cycle = 1;
            int X = 1;
            boolean[] pixels = new boolean[240];
            pixels[0] = true;
            String line;
            while((line = br.readLine()) != null) {
                cycle++;
                for(int i = -1; i < 2; i++) {
                    int crt = (cycle-1) % 40;
                    if(crt == X+i) {
                        pixels[cycle-1] = true;
                        break;
                    }
                }
                if(line.startsWith("addx")) {
                    int value = Integer.parseInt(line.substring(5));
                    cycle++;
                    X += value;
                    for(int i = -1; i < 2; i++) {
                        int crt = (cycle-1) % 40;
                        if(crt == X+i) {
                            pixels[cycle-1] = true;
                            break;
                        }
                    }
                } else if(!line.equals("noop")) {
                    throw new RuntimeException("Oups !");
                }
            }

            for(int i = 0; i < 240; i++) {
                if(i % 40 == 0) {
                    System.out.println();
                }
                if(pixels[i]) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
