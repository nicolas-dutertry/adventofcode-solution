package com.dutertry.adventofcode;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
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

    public AdventMap(int xSize, int ySize, char fillChar) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.lines = new ArrayList<>(ySize);
        for(int y = 0; y < ySize; y++) {
            lines.add(String.valueOf(fillChar).repeat(xSize));
        }
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
        PathFinder.WeightedPath<Point> weightedPath = PathFinder.findAnyBestPath(
                start,
                end,
                point -> this.getFreeNeighbors(point).stream().collect(Collectors.toMap(p -> p, p -> 1L)));
        return weightedPath == null ? null : weightedPath.path();
    }

    public Set<Point> getNeighbors(Point point) {
        Set<Point> possiblePoints = new HashSet<>();
        for(Direction direction : Direction.values()) {
            Point nextPoint = direction.move(point);
            Character nextChar = getChar(nextPoint);
            if(nextChar != null) {
                possiblePoints.add(nextPoint);
            }
        }
        return possiblePoints;
    }

    public Set<Point> getNeighbors(Point point, BiPredicate<Point, Character> filter) {
        Set<Point> possiblePoints = new HashSet<>();
        for(Direction direction : Direction.values()) {
            Point nextPoint = direction.move(point);
            Character nextChar = getChar(nextPoint);
            if(nextChar != null && filter.test(nextPoint, nextChar)) {
                possiblePoints.add(nextPoint);
            }
        }
        return possiblePoints;
    }

    public Set<Point> getFreeNeighbors(Point point) {
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

    public Set<Point> getStarNeighbors(Point point) {
        return getStarNeighbors(point, (p, c) -> true);
    }

    public Set<Point> getStarNeighbors(Point point, BiPredicate<Point, Character> filter) {
        Set<Point> possiblePoints = new HashSet<>();
        for(StarDirection direction : StarDirection.values()) {
            Point nextPoint = direction.move(point);
            Character nextChar = getChar(nextPoint);
            if(nextChar != null && filter.test(nextPoint, nextChar)) {
                possiblePoints.add(nextPoint);
            }
        }
        return possiblePoints;
    }

    public List<List<Point>> getBestPaths(Point start, Point end) {
        List<PathFinder.WeightedPath<Point>> weightedPaths = PathFinder.findAllBestPaths(
                start,
                end,
                point -> this.getFreeNeighbors(point).stream().collect(Collectors.toMap(p -> p, p -> 1L)));
        return weightedPaths.stream().map(PathFinder.WeightedPath::path).collect(Collectors.toList());
    }

}
