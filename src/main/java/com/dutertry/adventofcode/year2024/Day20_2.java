package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.List;

public class Day20_2 {
    public static void main(String[] args) {
        try {
            long total = 0;
            List<String> lines = AdventUtils.getLines(20);
            AdventMap map = new AdventMap(lines);

            Point startPoint = map.find('S');
            Point endPoint = map.find('E');
            List<Point> points = map.getBestPath(startPoint, endPoint);

            for(int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                for(int j = points.size()-1; j > i; j--) {
                    Point otherPoint = points.get(j);
                    int distance = point.distance(otherPoint);
                    int pathDistance = j - i;
                    if(distance <= 20 && distance + 100 <= pathDistance) {
                        total++;
                    }
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
