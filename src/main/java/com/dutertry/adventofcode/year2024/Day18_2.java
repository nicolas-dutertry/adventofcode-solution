package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day18_2 {
    private static final int MAX = 70;

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(18)) {
            long total = 0;

            List<Point> bytes = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null ) {
                String[] array = line.split(",");
                int x = Integer.parseInt(array[0]);
                int y = Integer.parseInt(array[1]);
                bytes.add(new Point(x, y));
            }

            List<String> lines = new ArrayList<>();
            for(int i = 0; i <= MAX; i++) {
                lines.add(".".repeat(MAX + 1));
            }

            int bytesCount = 1024;
            while(true) {
                AdventMap map = new AdventMap(lines);

                for (int i = 0; i < bytesCount; i++) {
                    Point p = bytes.get(i);
                    map.setChar(p, '#');
                }

                Point startPoint = new Point(0, 0);
                Point endPoint = new Point(MAX, MAX);
                Set<Point> visitedPoints = new HashSet<>();
                Set<Point> possiblePoints = new HashSet<>();
                possiblePoints.add(startPoint);
                visitedPoints.add(startPoint);
                boolean exit = true;
                main: while (true) {
                    Set<Point> nextPossiblePoints = new HashSet<>();
                    for (Point point : possiblePoints) {
                        Set<Point> points = getPossiblePoints(map, point);
                        for (Point nextPoint : points) {
                            if (nextPoint.equals(endPoint)) {
                                break main;
                            }
                            if (!visitedPoints.contains(nextPoint)) {
                                nextPossiblePoints.add(nextPoint);
                                visitedPoints.add(nextPoint);
                            }
                        }
                    }
                    if(nextPossiblePoints.isEmpty()) {
                        exit = false;
                        break;
                    }
                    possiblePoints = nextPossiblePoints;
                }
                if(!exit) {
                    Point p = bytes.get(bytesCount-1);
                    System.out.println(p.x() + "," + p.y());
                    break;
                }
                bytesCount++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Point> getPossiblePoints(AdventMap map, Point point) {
        Set<Point> possiblePoints = new HashSet<>();
        for(Direction direction : Direction.values()) {
            Point nextPoint = direction.move(point);
            Character nextChar = map.getChar(nextPoint);
            if(nextChar != null && nextChar != '#') {
                possiblePoints.add(nextPoint);
            }
        }
        return possiblePoints;
    }
}
