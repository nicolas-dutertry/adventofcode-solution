package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.List;

public class Day4_2 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(4);

            int maxx = lines.get(0).length();
            int maxy = lines.size();

            int count = 0;
            for(int x = 0; x < maxx; x++) {
                for(int y = 0; y < maxy; y++) {
                    if(isXmas(lines, x, y)) {
                        count++;
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

    private static boolean isXmas(List<String> lines, int x, int y) {
        if(getChar(lines, x, y) != 'A') {
            return false;
        }

        if( (getChar(lines, x-1, y-1) == 'M' && getChar(lines, x+1, y+1) == 'S') ||
                (getChar(lines, x-1, y-1) == 'S' && getChar(lines, x+1, y+1) == 'M')) {
            return (getChar(lines, x + 1, y - 1) == 'M' && getChar(lines, x - 1, y + 1) == 'S') ||
                    (getChar(lines, x + 1, y - 1) == 'S' && getChar(lines, x - 1, y + 1) == 'M');
        }
        return false;
    }

}
