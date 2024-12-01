package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day1_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            List<Integer> leftList = new LinkedList<>();
            Map<Integer, Integer> rightMap = new HashMap<>();
            while((line = br.readLine()) != null ) {
                String[] array = StringUtils.split(line);
                int left = Integer.parseInt(array[0]);
                int right = Integer.parseInt(array[1]);
                leftList.add(left);
                int count = rightMap.computeIfAbsent(right, k -> 0);
                count++;
                rightMap.put(right, count);
            }

            int total = 0;
            for (int i = 0; i < leftList.size(); i++) {
                int left = leftList.get(i);
                int count = rightMap.getOrDefault(left, 0);

                total += left * count;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
