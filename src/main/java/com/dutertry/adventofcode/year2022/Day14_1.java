package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day14_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(14)) {
            Set<Point> fullPoints = new HashSet<>();
            String line;
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line, " ->");
                Point point = parsePoint(array[0]);
                fullPoints.add(point);
                for(int i = 1; i < array.length; i++) {
                    Point nextPoint = parsePoint(array[i]);
                    fullPoints.add(nextPoint);

                    if(point.x() == nextPoint.x()) {
                        for(int y = Math.min(point.y(), nextPoint.y()); y <= Math.max(point.y(), nextPoint.y()); y++) {
                            fullPoints.add(new Point(point.x(), y));
                        }
                    } else {
                        for(int x = Math.min(point.x(), nextPoint.x()); x <= Math.max(point.x(), nextPoint.x()); x++) {
                            fullPoints.add(new Point(x, point.y()));
                        }
                    }
                    point = nextPoint;
                }
            }

            int maxy = fullPoints.stream().mapToInt(Point::y).max().getAsInt();

            Point startPoint = new Point(500, 0);
            Point currentSand = startPoint;
            int restCount = 0;
            while(currentSand.y() != maxy) {
                Point downPoint = new Point(currentSand.x(), currentSand.y() + 1);
                if(!fullPoints.contains(downPoint)) {
                    currentSand = downPoint;
                    continue;
                }
                Point downLeftPoint = new Point(currentSand.x() - 1, currentSand.y() + 1);
                if(!fullPoints.contains(downLeftPoint)) {
                    currentSand = downLeftPoint;
                    continue;
                }
                Point downRight = new Point(currentSand.x() + 1, currentSand.y() + 1);
                if(!fullPoints.contains(downRight)) {
                    currentSand = downRight;
                    continue;
                }

                fullPoints.add(currentSand);
                currentSand = startPoint;
                restCount++;
            }

            System.out.println(restCount);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Point parsePoint(String s) {
        String[] array = StringUtils.split(s, ",");
        int x = Integer.parseInt(array[0]);
        int y = Integer.parseInt(array[1]);
        return new Point(x, y);
    }
}
