package com.dutertry.adventofcode;

public enum Direction {
    UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

    private char symbol;

    private Direction(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public Direction opposite() {
        switch(this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction turnLeft() {
        switch(this) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction turnRight() {
        switch(this) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Point move(Point point) {
        switch(this) {
            case UP:
                return new Point(point.x(), point.y()-1);
            case DOWN:
                return new Point(point.x(), point.y()+1);
            case LEFT:
                return new Point(point.x()-1, point.y());
            case RIGHT:
                return new Point(point.x()+1, point.y());
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public static Direction fromSymbol(char symbol) {
        for(Direction direction : values()) {
            if(direction.symbol == symbol) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Unknown symbol: " + symbol);
    }
}
