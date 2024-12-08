package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day8_2 {

    public static void main(String[] args) {
        try {
            AdventMap adventMap = new AdventMap(AdventUtils.getLines(8));
            Set<Point> antiNodes = new HashSet<>();

            for(int x = 0; x < adventMap.getXSize(); x++) {
                for(int y = 0; y < adventMap.getYSize(); y++) {
                    char c = adventMap.getChar(x, y);
                    if(c == '.') {
                        continue;
                    }
                    List<Point> points = getPoints(adventMap, c);
                    for (Point point : points) {
                        if(point.equals(new Point(x, y))) {
                            continue;
                        }

                        int vx = point.x() - x;
                        int vy = point.y() - y;

                        Point antiNode = new Point(x, y);
                        while(adventMap.isPointInMap(antiNode)) {
                            antiNodes.add(antiNode);
                            antiNode = new Point(antiNode.x() - vx, antiNode.y() - vy);
                        }
                    }
                }
            }
            System.out.println(antiNodes.size());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Point> getPoints(AdventMap adventMap, char c) {
        List<Point> points = new LinkedList<>();
        for(int x = 0; x < adventMap.getXSize(); x++) {
            for (int y = 0; y < adventMap.getYSize(); y++) {
                if (adventMap.getChar(x, y) == c) {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }
}
