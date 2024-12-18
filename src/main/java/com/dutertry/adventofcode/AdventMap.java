package com.dutertry.adventofcode;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AdventMap {
    private final List<String> lines;
    private final int xSize;
    private final int ySize;

    public AdventMap(List<String> lines) {
        this.lines = lines;
        this.xSize = lines.get(0).length();
        this.ySize = lines.size();
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public boolean isPointInMap(Point point) {
        return point.x() >= 0 && point.x() < xSize && point.y() >= 0 && point.y() < ySize;
    }

    public Character getChar(int x, int y) {
        if(x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return null;
        }
        return lines.get(y).charAt(x);
    }

    public Character getChar(Point point) {
        return getChar(point.x(), point.y());
    }

    public void setChar(Point point, char c) {
        Character currentChar = getChar(point);
        if(currentChar == null || currentChar == c) {
            return;
        }

        StringBuilder sb = new StringBuilder(lines.get(point.y()));
        sb.setCharAt(point.x(), c);
        lines.set(point.y(), sb.toString());
    }

    public Point find(char c) {
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                if(getChar(x, y) == c) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    public Stream<Point> stream() {
        return IntStream.range(0, xSize)
            .boxed()
            .flatMap(x -> IntStream.range(0, ySize).mapToObj(y -> new Point(x, y)) );
    }

    public AdventMap cloneMap() {
        return new AdventMap(new ArrayList<>(lines));
    }

    public AdventMap rotateRight() {
        List<String> lines = new ArrayList<>(xSize);
        for(int x = 0; x < xSize; x++) {
            StringBuilder sb = new StringBuilder(ySize);
            for(int y = ySize - 1; y >= 0; y--) {
                sb.append(getChar(x, y));
            }
            lines.add(sb.toString());
        }
        return new AdventMap(lines);
    }

    public AdventMap rotateLeft() {
        List<String> lines = new ArrayList<>(xSize);
        for(int x = xSize - 1; x >= 0; x--) {
            StringBuilder sb = new StringBuilder(ySize);
            for(int y = 0; y < ySize; y++) {
                sb.append(getChar(x, y));
            }
            lines.add(sb.toString());
        }
        return new AdventMap(lines);
    }

    public void print(PrintStream out) {
        for(String line : lines) {
            out.println(line);
        }
    }

    public List<Point> getBestPath(Point start, Point end) {
        Set<Point> visitedPoints = new HashSet<>();
        List<List<Point>> possiblePaths = new LinkedList<>();
        possiblePaths.add(List.of(start));
        visitedPoints.add(start);
        main: while(!possiblePaths.isEmpty()) {
            List<List<Point>> nextPossiblePath = new LinkedList<>();
            for(List<Point> path : possiblePaths) {
                Point point = path.get(path.size() - 1);
                Set<Point> points = getFreeNeighbors(point);
                for(Point nextPoint : points) {
                    if(!visitedPoints.contains(nextPoint)) {
                        List<Point> newPath = new ArrayList<>(path);
                        newPath.add(nextPoint);
                        if(nextPoint.equals(end)) {
                            return newPath;
                        }
                        nextPossiblePath.add(newPath);
                        visitedPoints.add(nextPoint);
                    }
                }
            }
            possiblePaths = nextPossiblePath;
        }
        return null;
    }

    private Set<Point> getFreeNeighbors(Point point) {
        Set<Point> possiblePoints = new HashSet<>();
        for(Direction direction : Direction.values()) {
            Point nextPoint = direction.move(point);
            Character nextChar = getChar(nextPoint);
            if(nextChar != null && nextChar != '#') {
                possiblePoints.add(nextPoint);
            }
        }
        return possiblePoints;
    }
}
