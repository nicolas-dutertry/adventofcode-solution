package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.List;

public class Day4_1 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(4);

            int maxx = lines.get(0).length();
            int maxy = lines.size();

            int count = 0;
            for(int x = 0; x < maxx; x++) {
                for(int y = 0; y < maxy; y++) {
                    if(getChar(lines, x, y) == 'X') {
                        for(int dx = -1; dx < 2; dx++) {
                            for(int dy = -1; dy < 2; dy++) {
                                if(getChar(lines, x+dx, y+dy) == 'M' &&
                                        getChar(lines, x+2*dx, y+2*dy) == 'A' &&
                                        getChar(lines, x+3*dx, y+3*dy) == 'S') {
                                    count++;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static char getChar(List<String> lines, int x, int y) {
        if(x < 0 || x >= lines.get(0).length() || y < 0 || y >= lines.size()) {
            return ' ';
        }
        return lines.get(y).charAt(x);
    }
}
