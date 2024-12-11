package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day11_1 {
    private static final int MAX = 25;

    public static void main(String[] args) {
        try {
            String line  = AdventUtils.getString(11);
            List<String> array = Arrays.asList(StringUtils.split(line));
            for(int i = 0; i < MAX; i++) {
                List<String> newArray = new LinkedList<>();
                for (String s : array)  {
                    int size = s.length();
                    if(s.equals("0")) {
                        newArray.add("1");
                    } else if(size % 2 == 0) {
                        String s1 = s.substring(0, size / 2);
                        String s2 = s.substring(size / 2);
                        newArray.add(s1);
                        newArray.add(String.valueOf(Long.parseLong(s2)));
                    } else {
                        long val = Long.parseLong(s);
                        newArray.add(String.valueOf(val * 2024L));
                    }
                }
                array = newArray;
            }
            System.out.println(array.size());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
