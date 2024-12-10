package com.dutertry.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InfiniteMap {
    private final List<String> lines;
    private final int xSize;
    private final int ySize;

    public InfiniteMap(List<String> lines) {
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

    public Character getChar(int x, int y) {
        int xMod = Math.floorMod(x, xSize);
        int yMod = Math.floorMod(y, ySize);
        return lines.get(yMod).charAt(xMod);
    }

    public Character getChar(Point point) {
        return getChar(point.x(), point.y());
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

    public InfiniteMap rotateRight() {
        List<String> lines = new ArrayList<>(xSize);
        for(int x = 0; x < xSize; x++) {
            StringBuilder sb = new StringBuilder(ySize);
            for(int y = ySize - 1; y >= 0; y--) {
                sb.append(getChar(x, y));
            }
            lines.add(sb.toString());
        }
        return new InfiniteMap(lines);
    }

    public InfiniteMap rotateLeft() {
        List<String> lines = new ArrayList<>(xSize);
        for(int x = xSize - 1; x >= 0; x--) {
            StringBuilder sb = new StringBuilder(ySize);
            for(int y = 0; y < ySize; y++) {
                sb.append(getChar(x, y));
            }
            lines.add(sb.toString());
        }
        return new InfiniteMap(lines);
    }
}
