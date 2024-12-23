package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day21_2 {
    private record ParamCount(String sequence, int steps) {};
    private static final Map<ParamCount, Long> cacheCount = new HashMap<>();

    public static void main(String[] args) {
        try {
            List<String> codes = AdventUtils.getLines(21);

            List<String> listNum = List.of("789", "456", "123", "#0A");
            AdventMap mapNum = new AdventMap(listNum);

            List<String> listDir = List.of("#^A", "<v>");
            AdventMap mapRobot = new AdventMap(listDir);

            long total = 0;
            for(String code : codes) {
                Set<String> sequences = getSequences(code, mapNum);

                long minSize = Long.MAX_VALUE;
                for(String sequence : sequences) {
                    long count = count(sequence, mapRobot, 25);
                    minSize = Math.min(minSize, count);
                }
                total += (long)getValue(code) * minSize;
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long count(String sequence, AdventMap map, int steps) {
        if (steps == 0) {
            return sequence.length();
        }

        ParamCount param = new ParamCount(sequence, steps);
        if (cacheCount.containsKey(param)) {
            return cacheCount.get(param);
        }

        long count = 0;
        char c1 = 'A';
        for(int i = 0; i < sequence.length(); i++) {
            char c2 = sequence.charAt(i);
            Set<String> nextsequences = getSequences(map, c1, c2);
            long min = Long.MAX_VALUE;
            for (String nextsequence : nextsequences) {
                long nextcount = count(nextsequence, map, steps - 1);
                min = Math.min(min, nextcount);
            }
            count += min;
            c1 = c2;
        }

        cacheCount.put(param, count);
        return count;
    }

    private static List<String> cut(String s) {
        List<String> list = new LinkedList<>();
        int lastIndex = 0;
        while(true) {
            int i = s.indexOf('A', lastIndex);
            if(i == -1) {
                String sub = s.substring(lastIndex);
                if(StringUtils.isNotBlank(sub)) {
                    list.add(sub);
                }
                break;
            } else {
                list.add(s.substring(lastIndex, i + 1));
                lastIndex = i+1;
            }
        }
        return list;
    }

    private static Set<String> getSequences(String code, AdventMap map) {
        char c1 = 'A';
        Set<String> sequences = new HashSet<>();
        sequences.add("");
        for(int i = 0; i < code.length(); i++) {
            char c2 = code.charAt(i);
            Set<String> newsequences = new HashSet<>();
            Set<String> subSequences = getSequences(map, c1, c2);
            for (String sequence : sequences) {
                for (String subSequence : subSequences) {
                    newsequences.add(sequence + subSequence);
                }
            }

            c1 = c2;
            sequences = newsequences;
        }

        return sequences;
    }

    private static Set<String> getSequences(AdventMap map, char c1, char c2) {
        Set<String> sequences = new HashSet<>();
        if(c1 == c2) {
            sequences.add("A");
            return sequences;
        }

        Point start = map.find(c1);
        Point end = map.find(c2);
        List<List<Point>> bestPaths = map.getBestPaths(start, end);
        for (List<Point> bestPath : bestPaths) {
            sequences.add(convert(bestPath));
        }
        return sequences;
    }



    private static String convert(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            Point v = p2.subtract(p1);
            Direction direction = Direction.fromVector(v);
            sb.append(direction.getSymbol());
        }
        sb.append("A");
        return sb.toString();
    }

    private static int getValue(String code) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if(c >= '0' && c <= '9') {
                sb.append(c);
            }
        }
        return Integer.parseInt(sb.toString());
    }

}
