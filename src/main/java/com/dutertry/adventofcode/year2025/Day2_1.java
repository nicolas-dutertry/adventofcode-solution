package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Pair;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day2_1 {


    public static void main(String[] args) {
        try {
            String line = AdventUtils.getString(2, false);
            String[] strranges = StringUtils.split(line, ',');
            List<Pair<Long>> ranges = new LinkedList<>();
            for (String strrange : strranges) {
                String[] parts = StringUtils.split(strrange, '-');
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new Pair<>(start, end));
            }

            long total = 0;

            for(Pair<Long> range : ranges) {
                long start = range.getFirst();
                long end = range.getSecond();
                long index = start;
                while(index <= end) {
                    if(isRepetition(index)) {
                        total+=index;
                    }
                    index++;
                }
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isRepetition(long l) {
        String s = Long.toString(l);
        if(s.length() % 2 != 0) {
            return false;
        }
        String firstHalf = s.substring(0, s.length() / 2);
        String secondHalf = s.substring(s.length() / 2);
        return firstHalf.equals(secondHalf);
    }

}
