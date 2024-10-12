package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;

public class Day3_1 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(3)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null) {
                int middle = line.length() / 2;
                String s1 = line.substring(0, middle);
                String s2 = line.substring(middle);
                char c = getCommonChar(s1, s2);
                total += getPriority(c);
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPriority(char c) {
        if(c >= 'A' && c <= 'Z') {
            return 27 + c - 'A';
        } else if(c >= 'a' && c <= 'z') {
            return 1 + c - 'a';
        } else {
            throw new IllegalArgumentException("Invalid character: " + c);
        }
    }

    private static char getCommonChar(String s1, String s2) {
        for(int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if(s2.indexOf(c) != -1) {
                return c;
            }
        }
        throw new IllegalArgumentException("Oups");
    }
}
