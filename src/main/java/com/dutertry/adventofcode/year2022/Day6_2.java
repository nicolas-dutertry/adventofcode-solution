package com.dutertry.adventofcode.year2022;

import java.io.IOException;

public class Day6_2 {
    public static void main(String[] args) {
        try {
            String s = AdventUtils.getString(6);
            int size = 14;
            int current = size;
            main : while(true) {
                char[] chars = s.substring(current-size, current).toCharArray();
                for(int i = 0; i < size-1; i++) {
                    for(int j = i+1; j < size; j++) {
                        if(chars[i] == chars[j]) {
                            current++;
                            continue main;
                        }
                    }
                }
                break;
            }
            System.out.println(current);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
