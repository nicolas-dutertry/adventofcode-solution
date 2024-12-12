package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day12_2 {
    public static record Side(Point leftPoint, Point rightPoint, Direction direction) {}

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

                    Set<Side> sides = getSides(map, c, regionPoints);

                    total += regionPoints.size() * (long)sides.size();
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

    private static Set<Side> getSides(AdventMap map, char c, Set<Point> regionPoints) {
        Set<Side> sides = new HashSet<>();
        for (Point regionPoint : regionPoints) {
            for(Direction direction : Direction.values()) {
                Point newPoint = direction.move(regionPoint);
                Character newChar = map.getChar(newPoint);
                if(newChar == null || newChar != c) {
                    Side side = getSide(map, regionPoint, direction);
                    sides.add(side);
                }
            }
        }
        return sides;
    }

    public static Side getSide(AdventMap map, Point point, Direction direction) {
        char c = map.getChar(point);
        Direction leftDirection = direction.turnLeft();
        Direction rightDirection = direction.turnRight();
        Point leftPoint = point;
        while(true) {
            Point newPoint = leftDirection.move(leftPoint);
            Character newChar = map.getChar(newPoint);
            if(newChar == null || newChar != c) {
                break;
            }
            Point uppoint = direction.move(newPoint);
            Character upChar = map.getChar(uppoint);
            if(upChar != null && upChar == c) {
                break;
            }
            leftPoint = newPoint;
        }

        Point rightPoint = point;
        while(true) {
            Point newPoint = rightDirection.move(rightPoint);
            Character newChar = map.getChar(newPoint);
            if(newChar == null || newChar != c) {
                break;
            }
            Point uppoint = direction.move(newPoint);
            Character upChar = map.getChar(uppoint);
            if(upChar != null && upChar == c) {
                break;
            }
            rightPoint = newPoint;
        }

        return new Side(leftPoint, rightPoint, direction);
    }
}
