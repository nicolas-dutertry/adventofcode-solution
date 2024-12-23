package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day21_1 {
    private record ParamSeq(char c1, char c2) {}
    private static final Map<ParamSeq, Set<String>> cacheSeq = new HashMap<>();


    public static void main(String[] args) {
        try {
            List<String> codes = AdventUtils.getLines(21);

            List<String> listNum = List.of("789", "456", "123", "#0A");
            AdventMap mapNum = new AdventMap(listNum);

            List<String> listDir = List.of("#^A", "<v>");
            AdventMap mapRobot = new AdventMap(listDir);

            long total = 0;

            for(String code : codes) {
                Set<String> s1 = getSequences(code, mapNum);
                Set<String> s2 = getNext(s1, mapRobot);


                int minSize = Integer.MAX_VALUE;
                for (String s : s2) {
                    int size = getShortestSequenceLength(s, mapRobot);
                    if(size <= minSize) {
                        minSize = size;
                    }
                }

                total += (long)getValue(code) * minSize;
            }

            System.out.println(total);


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getNext(Set<String> s1, AdventMap map) {
        Set<String> s2 = new HashSet<>();
        int minSize = Integer.MAX_VALUE;
        for (String s : s1) {
            Set<String> sequences = getSequences(s, map);
            for(String sequence : sequences) {
                int size = sequence.length();
                if(size <= minSize) {
                    minSize = size;
                    s2.add(sequence);
                }
            }
        }

        Set<String> s3 = new HashSet<>();
        for (String s : s2) {
            if(s.length() == minSize) {
                s3.add(s);
            }
        }
        return s3;
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

    private static int getShortestSequenceLength(String code, AdventMap map) {
        char c1 = 'A';

        int length = 0;
        for(int i = 0; i < code.length(); i++) {
            char c2 = code.charAt(i);

            Set<String> subSequences = getSequences(map, c1, c2);
            int minLength = subSequences.stream().map(String::length).min(Integer::compareTo).get();
            length += minLength;

            c1 = c2;
        }

        return length;
    }

    private static Set<String> getSequences(AdventMap map, char c1, char c2) {
        ParamSeq param = new ParamSeq(c1, c2);
        if(cacheSeq.containsKey(param)) {
            return cacheSeq.get(param);
        }

        Set<String> sequences = new HashSet<>();
        if(c1 == c2) {
            sequences.add("A");
            cacheSeq.put(param, sequences);
            return sequences;
        }

        Point start = map.find(c1);
        Point end = map.find(c2);
        List<List<Point>> bestPaths = map.getBestPaths(start, end);
        for (List<Point> bestPath : bestPaths) {
            sequences.add(convert(bestPath));
        }
        cacheSeq.put(param, sequences);
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
