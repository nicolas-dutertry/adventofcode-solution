package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15_2 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(15)) {
            long total = 0;

            String line;
            List<String> lines = new LinkedList<>();
            StringBuilder moves = new StringBuilder();
            boolean isMove = false;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    isMove = true;
                    continue;
                }

                if(!isMove) {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        if(c == '@') {
                            sb.append("@.");
                        }
                        if(c == '#') {
                            sb.append("##");
                        }
                        if(c == '.') {
                            sb.append("..");
                        }
                        if(c == 'O') {
                            sb.append("[]");
                        }
                    }
                    lines.add(sb.toString());
                } else {
                    moves.append(line);
                }
            }

            AdventMap map = new AdventMap(lines);

            Point robotPoint = map.find('@');

            for(int i = 0; i < moves.length(); i++) {
                char dc = moves.charAt(i);
                Direction direction = Direction.fromSymbol(dc);
                Map<Point, Character> neighborPoints = new HashMap<>();
                neighborPoints.put(robotPoint, '@');
                populateNeighborPoints(map, robotPoint, direction, neighborPoints);

                if(neighborPoints.containsValue('#')) {
                    continue;
                }

                moveAll(map, neighborPoints, direction);

                robotPoint = direction.move(robotPoint);
            }

            for(int y = 0; y < map.getYSize(); y++) {
                for(int x = 0; x < map.getXSize(); x++) {
                    Point point = new Point(x, y);
                    char c = map.getChar(point);
                    if(c == '[') {
                        total += 100L*y + x;
                    }
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateNeighborPoints(AdventMap map, Point point, Direction direction, Map<Point, Character> neighborPoints) {
        Point neighborPoint = direction.move(point);
        char neighborChar = map.getChar(neighborPoint);
        if(neighborChar == '.' || neighborPoints.containsKey(neighborPoint)) {
            return;
        }

        neighborPoints.put(neighborPoint, neighborChar);
        if(neighborChar == '#') {
            return;
        }

        populateNeighborPoints(map, neighborPoint, direction, neighborPoints);

        Direction otherDirection = neighborChar == '[' ? Direction.RIGHT : Direction.LEFT;
        Point otherNeighborPoint = otherDirection.move(neighborPoint);
        char otherNeighborChar = map.getChar(otherNeighborPoint);
        if(!neighborPoints.containsKey(otherNeighborPoint)) {
            neighborPoints.put(otherNeighborPoint, otherNeighborChar);
            populateNeighborPoints(map, otherNeighborPoint, direction, neighborPoints);
        }
    }

    private static void moveAll(AdventMap map, Map<Point, Character> points, Direction direction) {
        Map<Point, Character> newPoints = new HashMap<>();
        for (Map.Entry<Point, Character> entry : points.entrySet()) {
            Point point = entry.getKey();
            char c = entry.getValue();
            newPoints.put(direction.move(point), c);
        }

        for(Point point : points.keySet()) {
            if(!newPoints.containsKey(point)) {
                map.setChar(point, '.');
            }
        }

        for (Map.Entry<Point, Character> entry : newPoints.entrySet()) {
            Point newPoint = entry.getKey();
            char newChar = entry.getValue();
            map.setChar(newPoint, newChar);
        }
    }
}
