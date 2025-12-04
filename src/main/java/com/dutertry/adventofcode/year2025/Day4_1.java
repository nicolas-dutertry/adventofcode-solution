package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Day4_1 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(4);
            AdventMap map = new AdventMap(lines);
            long count = map.stream().filter(p -> map.getChar(p) == '@').filter(p -> {
                Set<Point> neighbors = map.getStarNeighbors(p, (neighbor, c) -> c == '@');
                return neighbors.size() < 4;
            }).count();

            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
