package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Pair;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day2_2 {


    public static void main(String[] args) {
        try {
            String line = AdventUtils.getString(2);
            String[] strranges = StringUtils.split(line, ',');
            List<Pair<Long>> ranges = new LinkedList<>();
            long max = 0;
            for (String strrange : strranges) {
                String[] parts = StringUtils.split(strrange, '-');
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                ranges.add(new Pair<>(start, end));
                if (end > max) {
                    max = end;
                }
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
        for(int length = 1; length <= Long.toString(l).length() / 2; length++) {
            if(isRepetition(l, length)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRepetition(long l, int length) {
        String s = Long.toString(l);
        int numberLength = s.length();
        if(numberLength <= length) {
            return false;
        }
        if(numberLength % length != 0) {
            return false;
        }

        String pattern = s.substring(0, length);
        for(int i = 1; i < numberLength / length; i++) {
            String part = s.substring(i * length, (i + 1) * length);
            if(!part.equals(pattern)) {
                return false;
            }
        }
        return true;
    }

}
