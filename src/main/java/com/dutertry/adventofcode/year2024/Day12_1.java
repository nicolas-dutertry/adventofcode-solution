package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day12_1 {
    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(12));
            long total = 0;
            Set<Point> donePoints = new HashSet<>();
            for(int y = 0; y < map.getYSize(); y++) {
                for (int x = 0; x < map.getXSize(); x++) {
                    Point point = new Point(x, y);
                    if(donePoints.contains(point)) {
                        continue;
                    }
                    char c = map.getChar(point);
                    Set<Point> regionPoints = new HashSet<>();
                    regionPoints.add(point);
                    populateSameRegionPoints(map, c, point, regionPoints, donePoints);
                    total += regionPoints.size() * getPerimeter(regionPoints);
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void populateSameRegionPoints(AdventMap map, char c, Point point, Set<Point> regionPoints, Set<Point> donePoints) {
        for(Direction direction : Direction.values()) {
            Point neighbourPoint = direction.move(point);
            if(regionPoints.contains(neighbourPoint)) {
                continue;
            }

            Character neighbourChar = map.getChar(neighbourPoint);
            if(neighbourChar != null && neighbourChar == c) {
                regionPoints.add(neighbourPoint);
                donePoints.add(neighbourPoint);
                populateSameRegionPoints(map, c, neighbourPoint, regionPoints, donePoints);
            }
        }
    }

    public static long getPerimeter(Set<Point> points) {
        long perimeter = 0;
        for(Point point : points) {
            for(Direction direction : Direction.values()) {
                Point newPoint = direction.move(point);
                if(!points.contains(newPoint)) {
                    perimeter++;
                }
            }
        }
        return perimeter;
    }
}
