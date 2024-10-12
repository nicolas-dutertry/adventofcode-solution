package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.List;

public class Day8_1 {
    private static int ROW_COUNT;
    private static int COL_COUNT;
    public static void main(String[] args) {

        try {
            List<String> lines = AdventUtils.getLines(8);
            ROW_COUNT = lines.size();
            COL_COUNT = lines.get(0).length();
            int visibleCount = 0;

            for(int row = 0; row < ROW_COUNT; row++) {
                for(int col = 0; col < COL_COUNT; col++) {
                    int height = getHeight(lines, col, row);
                    if(isLeftVisible(lines, row, col, height)) {
                        visibleCount++;
                        continue;
                    }
                    if(isRightVisible(lines, row, col, height)) {
                        visibleCount++;
                        continue;
                    }
                    if(isTopVisible(lines, row, col, height)) {
                        visibleCount++;
                        continue;
                    }
                    if(isBottomVisible(lines, row, col, height)) {
                        visibleCount++;
                        continue;
                    }
                }
            }

            System.out.println(visibleCount);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getHeight(List<String> lines, int row, int col) {
        return Integer.parseInt(lines.get(row).substring(col, col+1));
    }

    private static boolean isLeftVisible(List<String> lines, int row, int col, int height) {
        for(int i = 0; i < col; i++) {
            int leftHeight = getHeight(lines, i, row);
            if(leftHeight >= height) {
                return false;
            }
        }
        return true;
    }

    private static boolean isRightVisible(List<String> lines, int row, int col, int height) {
        for(int i = col+1; i < COL_COUNT; i++) {
            int rightHeight = getHeight(lines, i, row);
            if(rightHeight >= height) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTopVisible(List<String> lines, int row, int col, int height) {
        for(int i = 0; i < row; i++) {
            int topHeight = getHeight(lines, col, i);
            if(topHeight >= height) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBottomVisible(List<String> lines, int row, int col, int height) {
        for(int i = row+1; i < ROW_COUNT; i++) {
            int bottomHeight = getHeight(lines, col, i);
            if(bottomHeight >= height) {
                return false;
            }
        }
        return true;
    }
}
