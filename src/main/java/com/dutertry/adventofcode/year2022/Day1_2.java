package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day1_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            List<Integer> totals = new LinkedList<>();
            int elf = 1;
            int total = 0;
            while((line = br.readLine()) != null) {
                if(StringUtils.isBlank(line)) {
                    totals.add(total);
                    total = 0;
                    elf++;
                } else {
                    total += Integer.parseInt(line);
                }
            }
            totals.add(total);
            totals.sort((o1, o2) -> o2 - o1);
            System.out.println(totals.get(0) + totals.get(1) + totals.get(2));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
