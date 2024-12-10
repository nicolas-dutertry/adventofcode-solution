package com.dutertry.adventofcode;

public record Point3D(int x, int y, int z) {
    public Point3D add(Point3D other) {
        return new Point3D(x + other.x, y + other.y, z + other.z);
    }

    public Point3D subtract(Point3D other) {
        return new Point3D(x - other.x, y - other.y, z - other.z);
    }

    public Point3D opposite() {
        return new Point3D(-x, -y, -z);
    }
}
