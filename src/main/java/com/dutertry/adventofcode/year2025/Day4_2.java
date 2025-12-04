package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Day4_2 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(4);
            AdventMap map = new AdventMap(lines);
            long total = 0;
            while(true) {
                List<Point> removedPoints = map.stream().filter(p -> map.getChar(p) == '@').filter(p -> {
                    Set<Point> neighbors = map.getStarNeighbors(p, (neighbor, c) -> c == '@');
                    return neighbors.size() < 4;
                }).toList();

                int size = removedPoints.size();

                if(size == 0) {
                    break;
                }

                total += size;

                for (Point p : removedPoints) {
                    map.setChar(p, '.');
                }
            }
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
