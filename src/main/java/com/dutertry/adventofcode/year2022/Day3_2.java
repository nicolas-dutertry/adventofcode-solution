package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;

public class Day3_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(3)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null) {
                String line2 = br.readLine();
                String line3 = br.readLine();
                String s1 = getCommonChars(line, line2);
                String s2 = getCommonChars(s1, line3);
                total += getPriority(s2.charAt(0));
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

    private static String getCommonChars(String s1, String s2) {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if(s2.indexOf(c) != -1) {
                s.append(c);
            }
        }
        return s.toString();
    }
}
