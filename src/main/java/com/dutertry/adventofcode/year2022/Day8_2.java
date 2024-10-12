package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.List;

public class Day8_2 {
    private static int ROW_COUNT;
    private static int COL_COUNT;
    public static void main(String[] args) {

        try {
            List<String> lines = AdventUtils.getLines(8);
            ROW_COUNT = lines.size();
            COL_COUNT = lines.get(0).length();
            int maxScenicScore = 0;

            for(int row = 0; row < ROW_COUNT; row++) {
                for(int col = 0; col < COL_COUNT; col++) {
                    int height = getHeight(lines, col, row);
                    int scenicScore = getLeftScore(lines, row, col, height) *
                            getRightScore(lines, row, col, height) *
                            getTopScore(lines, row, col, height) *
                            getBottomScore(lines, row, col, height);
                    if(scenicScore > maxScenicScore) {
                        maxScenicScore = scenicScore;
                    }
                }
            }

            System.out.println(maxScenicScore);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getHeight(List<String> lines, int row, int col) {
        return Integer.parseInt(lines.get(row).substring(col, col+1));
    }

    private static int getLeftScore(List<String> lines, int row, int col, int height) {
        int score = 0;
        for(int i = col-1; i >= 0; i--) {
            score++;
            int leftHeight = getHeight(lines, i, row);
            if(leftHeight >= height) {
                break;
            }
        }
        return score;
    }

    private static int getRightScore(List<String> lines, int row, int col, int height) {
        int score = 0;
        for(int i = col+1; i < COL_COUNT; i++) {
            score++;
            int rightHeight = getHeight(lines, i, row);
            if(rightHeight >= height) {
                break;
            }
        }
        return score;
    }

    private static int getTopScore(List<String> lines, int row, int col, int height) {
        int score = 0;
        for(int i = row-1; i >= 0; i--) {
            score++;
            int topHeight = getHeight(lines, col, i);
            if(topHeight >= height) {
                break;
            }
        }
        return score;
    }

    private static int getBottomScore(List<String> lines, int row, int col, int height) {
        int score = 0;
        for(int i = row+1; i < ROW_COUNT; i++) {
            score++;
            int bottomHeight = getHeight(lines, col, i);
            if(bottomHeight >= height) {
                break;
            }
        }
        return score;
    }
}
