package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Pair;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day9_1 {
    private record Rectangle(Pair<Point> pair, long area) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(9);
            List<Point> points = new LinkedList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
            List<Rectangle> rectangles = getSortedRectangles(points);
            System.out.println(rectangles.get(0).area);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long getArea(Point p1, Point p2) {
        return (Math.abs((long)((p1.x() - p2.x()))) + 1) * (Math.abs((long)((p1.y() - p2.y()))) + 1);
    }

    private static List<Rectangle> getSortedRectangles(List<Point> points) {
        List<Rectangle> connections = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);
                Pair<Point> pair = new Pair<>(p1, p2);
                long distance = getArea(p1, p2);
                connections.add(new Rectangle(pair, distance));
            }
        }
        connections.sort((c1, c2) -> Long.compare(c2.area, c1.area));

        return connections;
    }
}
