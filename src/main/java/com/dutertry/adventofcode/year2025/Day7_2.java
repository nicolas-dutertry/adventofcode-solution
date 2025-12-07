package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7_2 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(7);
            AdventMap map = new AdventMap(lines);
            Point start = map.find('S');
            Map<Point, Long> countMap = Map.of(start, 1L);
            main : while(true) {
                Map<Point, Long> nextCountMap = new HashMap<>();
                for(Map.Entry<Point, Long> entry : countMap.entrySet()) {
                    Point tachyon = entry.getKey();
                    long count = entry.getValue();
                    Point down = Direction.DOWN.move(tachyon);
                    Character downChar = map.getChar(down);
                    if(downChar == null) {
                        break main;
                    }
                    if(downChar == '.') {
                        nextCountMap.merge(down, count, Long::sum);
                    } else if(downChar == '^') {
                        Point downLeft = Direction.LEFT.move(down);
                        nextCountMap.merge(downLeft, count, Long::sum);

                        Point downRight = Direction.RIGHT.move(down);
                        nextCountMap.merge(downRight, count, Long::sum);
                    } else {
                        throw new RuntimeException("oups");
                    }
                }
                countMap = nextCountMap;
            }
            long total = countMap.values().stream().mapToLong(Long::longValue).sum();
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
