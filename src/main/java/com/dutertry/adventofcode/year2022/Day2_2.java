package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
            String line;
            int score = 0;
            while((line = br.readLine()) != null) {
                char elf = line.charAt(0);
                char res = line.charAt(2);
                char me = getExpectedMe(elf, res);
                score += getScore(me) + getScore(elf, me);
            }
            System.out.println(score);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getScore(char me) {
        return switch (me) {
            case 'X' -> 1;
            case 'Y' -> 2;
            case 'Z' -> 3;
            default -> throw new IllegalArgumentException("Invalid character: " + me);
        };
    }

    private static int getScore(char elf, char me) {
        return switch (me) {
            case 'X' -> elf == 'A' ? 3 : elf == 'B' ? 0 : 6;
            case 'Y' -> elf == 'A' ? 6 : elf == 'B' ? 3 : 0;
            case 'Z' -> elf == 'A' ? 0 : elf == 'B' ? 6 : 3;
            default -> throw new IllegalArgumentException("Invalid character: " + me);
        };
    }

    private static char getExpectedMe(char elf, char res) {
        return switch (res) {
            case 'X' -> elf == 'A' ? 'Z' : elf == 'B' ? 'X' : 'Y';
            case 'Y' -> elf == 'A' ? 'X' : elf == 'B' ? 'Y' : 'Z';
            case 'Z' -> elf == 'A' ? 'Y' : elf == 'B' ? 'Z' : 'X';
            default -> throw new IllegalArgumentException("Invalid character: " + res);
        };
    }
}
