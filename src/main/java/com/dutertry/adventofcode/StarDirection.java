package com.dutertry.adventofcode;

public enum StarDirection {
    UP(new Point(0,-1)),
    DOWN(new Point(0,1)),
    LEFT( new Point(-1,0)),
    RIGHT(new Point(1,0)),
    UP_RIGHT(new Point(1,-1)),
    UP_LEFT(new Point(-1,-1)),
    DOWN_RIGHT(new Point(1,1)),
    DOWN_LEFT(new Point(-1,1));

    private final Point vector;

    private StarDirection(Point vector) {
        this.vector = vector;
    }

    public Point getVector() {
        return vector;
    }

    public StarDirection opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP_RIGHT -> DOWN_LEFT;
            case UP_LEFT -> DOWN_RIGHT;
            case DOWN_RIGHT -> UP_LEFT;
            case DOWN_LEFT -> UP_RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public StarDirection turnLeft() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
            case UP_RIGHT -> UP_LEFT;
            case UP_LEFT -> DOWN_LEFT;
            case DOWN_RIGHT -> UP_RIGHT;
            case DOWN_LEFT -> DOWN_RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public StarDirection turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
            case UP_RIGHT -> DOWN_RIGHT;
            case UP_LEFT -> UP_RIGHT;
            case DOWN_RIGHT -> DOWN_LEFT;
            case DOWN_LEFT -> UP_LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Point move(Point point) {
        return point.add(vector);
    }
}
