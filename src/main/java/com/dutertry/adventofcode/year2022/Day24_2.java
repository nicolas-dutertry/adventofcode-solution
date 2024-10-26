package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day24_2 {
    enum Direction {
        UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

        private final char c;

        private Direction(char c) {
            this.c = c;
        }

        private static Direction getDirection(char c) {
            Direction[] directions = Direction.values();
            for(Direction direction : directions) {
                if(direction.c == c) {
                    return direction;
                }
            }
            throw new IllegalArgumentException("Invalid direction: " + c);
        }
    }

    private static record Blizzard(Point position, Direction direction) {}

    private static record State(Point position, int tripNumber) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(24);
            Set<Blizzard> blizzards = new HashSet<>();
            int width = lines.get(0).length()-2;
            int height = lines.size()-2;
            for(int i = 1; i < lines.size() -1; i++) {
                String line = lines.get(i);
                for(int j = 1; j < line.length()-1; j++) {
                    char c = line.charAt(j);
                    if(c == '.') {
                        continue;
                    }
                    Direction direction = Direction.getDirection(c);
                    blizzards.add(new Blizzard(new Point(j-1, i-1), Direction.getDirection(c)));
                }
            }

            Point initialPosition = new Point(0, -1);
            State initialState = new State(initialPosition, 1);
            Point finalDestination = new Point(width-1, height);
            Set<State> states = Collections.singleton(initialState);
            int step = 1;
            while(true) {
                Set<State> nextStates = new HashSet<>();
                Set<Point> occupiedPoints = new HashSet<>();
                Set<Blizzard> nextBlizzards = moveBlizzards(blizzards, width, height, occupiedPoints);
                stateLoop : for(State state : states) {
                    List<Point> possiblePositions = List.of(
                            new Point(state.position().x(), state.position().y()),
                            new Point(state.position().x(), state.position().y()-1),
                            new Point(state.position().x(), state.position().y()+1),
                            new Point(state.position().x()-1, state.position().y()),
                            new Point(state.position().x()+1, state.position().y()));
                    for(Point possiblePosition : possiblePositions) {
                        if(state.tripNumber == 1 && possiblePosition.equals(finalDestination)) {
                            nextStates.add(new State(possiblePosition, 2));
                            continue;
                        }
                        if(state.tripNumber == 2 && possiblePosition.equals(initialPosition)) {
                            nextStates.add(new State(possiblePosition, 3));
                            continue;
                        }
                        if(state.tripNumber == 3 && possiblePosition.equals(finalDestination)) {
                            System.out.println(step);
                            return;
                        }
                        if(isValid(possiblePosition, occupiedPoints, width, height)) {
                            nextStates.add(new State(possiblePosition, state.tripNumber));
                        }
                    }
                }
                if(nextStates.isEmpty()) {
                    throw new IllegalStateException("No more possible moves");
                }
                states = nextStates;
                blizzards = nextBlizzards;
                step++;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(Point position, Set<Point> occupiedPoints, int width, int height) {
        if(position.x() == 0 && position.y() == -1) {
            return true;
        }
        if(position.x() == width-1 && position.y() == height) {
            return true;
        }

        if(position.x() < 0 || position.x() >= width || position.y() < 0 || position.y() >= height) {
            return false;
        }
        return !occupiedPoints.contains(position);
    }

    private static Set<Blizzard> moveBlizzards(Set<Blizzard> blizzards, int width, int height, Set<Point> occupiedPoints) {
        Set<Blizzard> newBlizzards = new HashSet<>();
        for(Blizzard blizzard : blizzards) {
            Point newPosition = switch (blizzard.direction()) {
                case UP -> new Point(blizzard.position().x(), blizzard.position().y()-1);
                case DOWN -> new Point(blizzard.position().x(), blizzard.position().y()+1);
                case LEFT -> new Point(blizzard.position().x()-1, blizzard.position().y());
                case RIGHT -> new Point(blizzard.position().x()+1, blizzard.position().y());
            };

            if(newPosition.x() < 0) {
                newPosition = new Point(width-1, newPosition.y());
            }
            if(newPosition.x() >= width) {
                newPosition = new Point(0, newPosition.y());
            }
            if(newPosition.y() < 0) {
                newPosition = new Point(newPosition.x(), height-1);
            }
            if(newPosition.y() >= height) {
                newPosition = new Point(newPosition.x(), 0);
            }
            newBlizzards.add(new Blizzard(newPosition, blizzard.direction()));
            occupiedPoints.add(newPosition);
        }
        return newBlizzards;
    }
}
