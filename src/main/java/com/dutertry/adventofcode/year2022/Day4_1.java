package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Day4_1 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(4)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line, ",");
                int[] elf1 = Arrays.stream(StringUtils.split(array[0], "-")).map(Integer::parseInt).mapToInt(i -> i).toArray();
                int[] elf2 = Arrays.stream(StringUtils.split(array[1], "-")).map(Integer::parseInt).mapToInt(i -> i).toArray();

                if(elf1[0] <= elf2[0] && elf1[1] >= elf2[1]) {
                    total++;
                } else if(elf2[0] <= elf1[0] && elf2[1] >= elf1[1]) {
                    total++;
                }
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
