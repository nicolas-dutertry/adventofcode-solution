package com.dutertry.adventofcode;

public record Point(int x, int y) {
    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point subtract(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point opposite() {
        return new Point(-x, -y);
    }

    public int distance(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
}
