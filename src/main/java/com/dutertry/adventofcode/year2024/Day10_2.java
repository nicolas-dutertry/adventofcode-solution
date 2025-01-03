package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day10_2 {

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
                    List<Point> lastPoints = getReachablePoints(map, c, List.of(point));
                    total += lastPoints.size();
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Point> getReachablePoints(AdventMap map, char c, List<Point> points) {
        if(c == '9') {
            return points;
        }
        List<Point> newPoints = new LinkedList<>();
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
