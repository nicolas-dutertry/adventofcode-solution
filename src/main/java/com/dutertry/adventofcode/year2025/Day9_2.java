package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Pair;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day9_2 {
    private record Rectangle(Pair<Point> pair, long area) {}
    private record RowSegment(int y, int minX, int maxX) {}
    private record ColSegment(int x, int minY, int maxY) {}
    private record RectangleCorners(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(9);
            List<Point> points = new LinkedList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }

            Map<Integer, List<Point>> colMap = new HashMap<>();
            Map<Integer, List<Point>> rowMap = new HashMap<>();
            for (Point point : points) {
                colMap.putIfAbsent(point.x(), new ArrayList<>());
                colMap.get(point.x()).add(point);
                rowMap.putIfAbsent(point.y(), new ArrayList<>());
                rowMap.get(point.y()).add(point);
            }

            List<RowSegment> rowSegments = new LinkedList<>();
            for(Map.Entry<Integer, List<Point>> entry : rowMap.entrySet()) {
                int y = entry.getKey();
                List<Point> pointList = entry.getValue();
                Point first = pointList.get(0);
                Point second = pointList.get(1);
                RowSegment rowSegment = new RowSegment(y, Math.min(first.x(), second.x()), Math.max(first.x(), second.x()));
                rowSegments.add(rowSegment);
            }
            rowSegments.sort((s1, s2) -> Integer.compare(s1.y, s2.y));

            List<ColSegment> colSegments = new LinkedList<>();
            for(Map.Entry<Integer, List<Point>> entry : colMap.entrySet()) {
                int x = entry.getKey();
                List<Point> pointList = entry.getValue();
                Point first = pointList.get(0);
                Point second = pointList.get(1);
                ColSegment colSegment = new ColSegment(x, Math.min(first.y(), second.y()), Math.max(first.y(), second.y()));
                colSegments.add(colSegment);
            }
            colSegments.sort((s1, s2) -> Integer.compare(s1.x, s2.x));

            List<Rectangle> rectangles = getSortedRectangles(points, rowSegments, colSegments);
            System.out.println(rectangles.get(0).area);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long getArea(Point p1, Point p2) {
        return (Math.abs((long)((p1.x() - p2.x()))) + 1) * (Math.abs((long)((p1.y() - p2.y()))) + 1);
    }

    private static List<Rectangle> getSortedRectangles(List<Point> points, List<RowSegment> rowSegments, List<ColSegment> colSegments) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);
                Pair<Point> pair = new Pair<>(p1, p2);
                long distance = getArea(p1, p2);
                Rectangle rectangle = new Rectangle(pair, distance);
                if(checkRectangle(rectangle, rowSegments, colSegments)) {
                    rectangles.add(rectangle);
                }
            }
        }
        rectangles.sort((c1, c2) -> Long.compare(c2.area, c1.area));
        return rectangles;
    }

    private static RectangleCorners getRectangleCorners(Point p1, Point p2) {
        Point topLeft = new Point(Math.min(p1.x(), p2.x()), Math.min(p1.y(), p2.y()));
        Point bottomRight = new Point(Math.max(p1.x(), p2.x()), Math.max(p1.y(), p2.y()));
        Point topRight = new Point(bottomRight.x(), topLeft.y());
        Point bottomLeft = new Point(topLeft.x(), bottomRight.y());
        return new RectangleCorners(topLeft, topRight, bottomLeft, bottomRight);
    }

    private static boolean isInside(Point point, List<RowSegment> rowSegments, List<ColSegment> colSegments) {
        for (RowSegment rowSegment : rowSegments) {
            if (rowSegment.y == point.y() && rowSegment.minX <= point.x() && rowSegment.maxX >= point.x()) {
                return true;
            }
        }
        for (ColSegment colSegment : colSegments) {
            if (colSegment.x == point.x() && colSegment.minY <= point.y() && colSegment.maxY >= point.y()) {
                return true;
            }
        }
        int intersections = 0;
        double x = point.x() + 0.5;
        for (RowSegment rowSegment : rowSegments) {
            if(rowSegment.y > point.y()) {
                continue;
            }
            if(rowSegment.minX <= x && rowSegment.maxX >= x) {
                intersections++;
            }
        }
        return intersections % 2 == 1;
    }

    private static boolean checkRectangle(Rectangle rectangle, List<RowSegment> rowSegments, List<ColSegment> colSegments) {
        RectangleCorners corners = getRectangleCorners(rectangle.pair.getFirst(), rectangle.pair.getSecond());
        if(!isInside(corners.topLeft, rowSegments, colSegments)) {
            return false;
        }
        if(!isInside(corners.topRight, rowSegments, colSegments)) {
            return false;
        }
        if(!isInside(corners.bottomLeft, rowSegments, colSegments)) {
            return false;
        }
        if(!isInside(corners.bottomRight, rowSegments, colSegments)) {
            return false;
        }

        for(RowSegment rowSegment : rowSegments) {
            if(rowSegment.y >= corners.bottomRight.y() || rowSegment.y <= corners.topLeft.y()) {
                continue;
            }
            if(rowSegment.minX < corners.topRight.x() && rowSegment.maxX > corners.topLeft.x()) {
                return false;
            }
        }

        for(ColSegment colSegment : colSegments) {
            if(colSegment.x >= corners.bottomRight.x() || colSegment.x <= corners.topLeft.x()) {
                continue;
            }
            if(colSegment.minY < corners.bottomLeft.y() && colSegment.maxY > corners.topLeft.y()) {
                return false;
            }
        }
        return true;
    }
}
