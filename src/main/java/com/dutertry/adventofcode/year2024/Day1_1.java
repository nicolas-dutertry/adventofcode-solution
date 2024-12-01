package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day1_1 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(1)) {
            String line;
            List<Integer> leftList = new LinkedList<>();
            List<Integer> rightList = new LinkedList<>();
            while((line = br.readLine()) != null ) {
                String[] array = StringUtils.split(line);
                int left = Integer.parseInt(array[0]);
                int right = Integer.parseInt(array[1]);
                leftList.add(left);
                rightList.add(right);
            }
            leftList.sort(Integer::compareTo);
            rightList.sort(Integer::compareTo);

            int total = 0;
            for (int i = 0; i < leftList.size(); i++) {
                int left = leftList.get(i);
                int right = rightList.get(i);

                total += Math.abs(right-left);
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
