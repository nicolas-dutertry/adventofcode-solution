package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day10_1 {

    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(10));
            long total = 0;

            for(int y = 0; y < map.getYSize(); y++) {
                for(int x = 0; x < map.getXSize(); x++) {
                    Point point = new Point(x, y);
                    char c = map.getChar(point);
                    if(c != '0') {
                        continue;
                    }
                    Set<Point> lastPoints = getReachablePoints(map, c, Set.of(point));
                    total += lastPoints.size();
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Point> getReachablePoints(AdventMap map, char c, Set<Point> points) {
        if(c == '9') {
            return points;
        }
        Set<Point> newPoints = new HashSet<>();
        char expectedChar = (char)(c+1);

        for (Point point : points) {
            for(Direction direction : Direction.values()) {
                Point newPoint = direction.move(point);
                Character newChar = map.getChar(newPoint);
                if(newChar != null && newChar == expectedChar) {
                    newPoints.add(newPoint);
                }
            }
        }
        return getReachablePoints(map, expectedChar, newPoints);
    }
}
