package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day12_2 {
    public static void main(String[] args) {
        Map<Point, Character> map = new HashMap<>();
        int y = 0;
        List<Point> startPoints = new LinkedList<>();
        Point endPoint = null;
        try(BufferedReader br = AdventUtils.getBufferedReader(12)) {
            String line;
            while((line = br.readLine()) != null) {
                for(int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    Point point = new Point(x, y);
                    if(c == 'S') {
                        c = 'a';
                    } else if(c == 'E') {
                        endPoint = point;
                        c = 'z';
                    }

                    if(c == 'a') {
                        startPoints.add(point);
                    }
                    map.put(point, c);
                }
                y++;
            }

            int minStep = Integer.MAX_VALUE;

            for(Point startPoint : startPoints) {
                Set<Point> visitedPoints = new HashSet<>();
                visitedPoints.add(startPoint);
                Set<Point> currentPoints = new HashSet<>();
                currentPoints.add(startPoint);
                int step = 0;
                main:
                while (!currentPoints.isEmpty()) {
                    step++;
                    Set<Point> newCurrentPoints = new HashSet<>();
                    for (Point point : currentPoints) {
                        List<Point> nextPoints = List.of(
                                new Point(point.x(), point.y() - 1),
                                new Point(point.x(), point.y() + 1),
                                new Point(point.x() - 1, point.y()),
                                new Point(point.x() + 1, point.y()));

                        for (Point nextPoint : nextPoints) {
                            if (canGo(map, visitedPoints, point, nextPoint)) {
                                newCurrentPoints.add(nextPoint);
                                visitedPoints.add(nextPoint);

                                if (nextPoint.equals(endPoint)) {
                                    break main;
                                }
                            }
                        }
                    }
                    currentPoints = newCurrentPoints;
                }

                if(!currentPoints.isEmpty() && step < minStep) {
                    minStep = step;
                }
            }

            System.out.println(minStep);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean canGo(Map<Point, Character> map, Set<Point> visitedPoints, Point from, Point to) {
        if(visitedPoints.contains(to)) {
            return false;
        }
        char cFrom = map.get(from);
        Character cTo = map.get(to);
        if(cTo == null || cTo == 'a') {
            return false;
        }
        return cTo - cFrom <= 1;
    }
}
