package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Interval;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day5_2 {

    public static void main(String[] args) {
        List<Interval> ranges = new LinkedList<>();
        try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    break;
                }

                String[] parts = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new Interval(start, end));
            }

            long count = 0;
            List<Interval> intervals = Interval.mergeAll(ranges);
            for(Interval interval : intervals) {
                count += interval.max() - interval.min() + 1;
            }

            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
