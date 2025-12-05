package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Interval;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day5_1 {
    public static void main(String[] args) {
        boolean range = true;
        List<Interval> ranges = new LinkedList<>();
        List<Long> items = new LinkedList<>();
        try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    range = false;
                    continue;
                }
                if(range) {
                    String[] parts = line.split("-");
                    long start = Long.parseLong(parts[0]);
                    long end = Long.parseLong(parts[1]);
                    ranges.add(new Interval(start, end));
                } else {
                    long item = Long.parseLong(line);
                    items.add(item);
                }
            }

            long count = 0;
            for(Long item : items) {
                if(contains(item, ranges)) {
                    count++;
                }
            }

            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean contains(long item, List<Interval> ranges) {
        for(Interval range : ranges) {
            if(range.contains(item)) {
                return true;
            }
        }
        return false;
    }
}
