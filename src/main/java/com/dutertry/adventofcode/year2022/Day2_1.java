package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2_1 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
            String line;
            int score = 0;
            while((line = br.readLine()) != null) {
                char elf = line.charAt(0);
                char me = line.charAt(2);
                score += getScore(me) + getScore(elf, me);
            }
            System.out.println(score);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getScore(char me) {
        switch (me) {
            case 'X':
                return 1;
            case 'Y':
                return 2;
            case 'Z':
                return 3;
            default:
                throw new IllegalArgumentException("Invalid character: " + me);
        }
    }

    private static int getScore(char elf, char me) {
        return switch (me) {
            case 'X' -> elf == 'A' ? 3 : elf == 'B' ? 0 : 6;
            case 'Y' -> elf == 'A' ? 6 : elf == 'B' ? 3 : 0;
            case 'Z' -> elf == 'A' ? 0 : elf == 'B' ? 6 : 3;
            default -> throw new IllegalArgumentException("Invalid character: " + me);
        };
    }
}
