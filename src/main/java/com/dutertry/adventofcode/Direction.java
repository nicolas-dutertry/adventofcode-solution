package com.dutertry.adventofcode;

public enum Direction {
    UP('^', new Point(0,-1)),
    DOWN('v', new Point(0,1)),
    LEFT('<', new Point(-1,0)),
    RIGHT('>', new Point(1,0));

    private final char symbol;
    private final Point vector;

    private Direction(char symbol, Point vector) {
        this.symbol = symbol;
        this.vector = vector;
    }

    public char getSymbol() {
        return symbol;
    }

    public Point getVector() {
        return vector;
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public Point move(Point point) {
        return point.add(vector);
    }

    public static Direction fromSymbol(char symbol) {
        for(Direction direction : values()) {
            if(direction.symbol == symbol) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Unknown symbol: " + symbol);
    }

    public static Direction fromVector(Point vector) {
        for(Direction direction : values()) {
            if(direction.vector.equals(vector)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Unknown vector: " + vector);
    }
}
