package com.dutertry.adventofcode;

import java.util.ArrayList;
import java.util.List;
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
}
