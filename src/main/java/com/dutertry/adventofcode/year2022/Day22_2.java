package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day22_2 {

    enum Direction {
        //Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
        UP(3), RIGHT(0), DOWN(1), LEFT(2);

        static {
            UP.left = LEFT;
            UP.right = RIGHT;
            UP.opposite = DOWN;

            RIGHT.left = UP;
            RIGHT.right = DOWN;
            RIGHT.opposite = LEFT;

            DOWN.left = RIGHT;
            DOWN.right = LEFT;
            DOWN.opposite = UP;

            LEFT.left = DOWN;
            LEFT.right = UP;
            LEFT.opposite = RIGHT;
        }

        private final int score;
        private Direction left;
        private Direction right;
        private Direction opposite;

        private Direction(int score) {
            this.score = score;
        }

        public Direction move(char move) {
            if(move == 'R') {
                return right;
            } else {
                return left;
            }
        }
    }

    private static record State(Point position, Direction direction) {}

    private static record Edge(int id, boolean bis, Point p1, Point p2, Direction enterDirection) {}

    private static final List<Edge> edges = List.of(
            new Edge(1, false, new Point(50, 0), new Point(99, 0), Direction.DOWN),
            new Edge(1, true, new Point(0, 150), new Point(0, 199), Direction.RIGHT),

            new Edge(2, false, new Point(99, 50), new Point(99, 99), Direction.LEFT),
            new Edge(2, true, new Point(100, 49), new Point(149, 49), Direction.UP),

            new Edge(3, false, new Point(50, 149), new Point(99, 149), Direction.UP),
            new Edge(3, true, new Point(49, 150), new Point(49, 199), Direction.LEFT),

            new Edge(4, false, new Point(50, 50), new Point(50, 99), Direction.RIGHT),
            new Edge(4, true, new Point(0, 100), new Point(49, 100), Direction.DOWN),

            new Edge(5, false, new Point(50, 0), new Point(50, 49), Direction.RIGHT),
            new Edge(5, true, new Point(0, 100), new Point(0, 149), Direction.RIGHT),

            new Edge(6, false, new Point(149, 0), new Point(149, 49), Direction.LEFT),
            new Edge(6, true, new Point(99, 100), new Point(99, 149), Direction.LEFT),

            new Edge(7, false, new Point(100, 0), new Point(149, 0), Direction.DOWN),
            new Edge(7, true, new Point(0, 199), new Point(49, 199), Direction.UP)
    );

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(22);
            String path = lines.remove(lines.size()-1);
            List<Object> moves = parsePath(path);

            lines.remove(lines.size()-1);

            int length = lines.stream().mapToInt(String::length).max().orElse(0);
            lines = lines.stream().map(s -> StringUtils.rightPad(s, length)).toList();

            int y = 0;
            int x = lines.get(0).indexOf('.');

            State state = new State(new Point(x, y), Direction.RIGHT);

            for(Object move : moves) {
                if(move instanceof Character) {
                    state = new State(state.position, state.direction.move((char)move));
                } else {
                    int steps = (int)move;
                    state = move(lines, state, steps);
                }
            }

            int res = 1000*(state.position.y()+1) + 4*(state.position.x()+1) + state.direction.score;
            System.out.println(res);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object> parsePath(String path) {
        List<Object> moves = new LinkedList<>();
        String current = "";
        for(int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if(c == 'R' || c == 'L') {
                if(!current.isEmpty()) {
                    moves.add(Integer.parseInt(current));
                }
                current = "";
                moves.add(c);
            } else {
                current += c;
            }
        }

        if(!current.isEmpty()) {
            moves.add(Integer.parseInt(current));
        }
        return moves;
    }

    private static State move(List<String> lines, State state, int steps) {
        for(int i = 0; i < steps; i++) {
            State nextState = getNextState(lines, state);
            char c = getChar(lines, nextState.position);
            if(c == '.') {
                state = nextState;
            } else if(c == '#') {
                break;
            } else {
                throw new RuntimeException("Oups");
            }
        }
        return state;
    }

    private static char getChar(List<String> lines, Point position) {
        if(position.y() >= lines.size() || position.y() < 0) {
            return ' ';
        }
        String line = lines.get(position.y());
        if(position.x() >= line.length() || position.x() < 0) {
            return ' ';
        }
        return line.charAt(position.x());
    }

    private static State getNextState(List<String> lines, State state) {
        Point nextPosition = switch (state.direction) {
            case UP -> new Point(state.position.x(), state.position.y() - 1);
            case RIGHT -> new Point(state.position.x() + 1, state.position.y());
            case DOWN -> new Point(state.position.x(), state.position.y() + 1);
            case LEFT -> new Point(state.position.x() - 1, state.position.y());
            default -> throw new RuntimeException("Oups");
        };

        char nextChar = getChar(lines, nextPosition);
        if(nextChar == ' ') {
            Edge edge = getExitEdge(state);
            int index = getIndexFromLeft(state, edge);
            Edge connectedEdge = getConnectedEdge(edge);
            return getEnteringState(connectedEdge, index);
        } else {
            return new State(nextPosition, state.direction);
        }
    }

    private static Edge getExitEdge(State state) {
        for(Edge edge : edges) {
            if(edge.enterDirection.opposite == state.direction) {
                if(state.direction == Direction.UP || state.direction == Direction.DOWN) {
                    if(state.position.y() == edge.p1.y() && state.position.x() >= edge.p1.x() && state.position.x() <= edge.p2.x()) {
                        return edge;
                    }
                } else {
                    if(state.position.x() == edge.p1.x() && state.position.y() >= edge.p1.y() && state.position.y() <= edge.p2.y()) {
                        return edge;
                    }
                }
            }
        }
        throw new RuntimeException("Oups");
    }

    private static Edge getConnectedEdge(Edge edge) {
        for(Edge e : edges) {
            if(e.id == edge.id && e.bis != edge.bis) {
                return e;
            }
        }
        throw new RuntimeException("Oups");
    }

    private static int getIndexFromLeft(State state, Edge edge) {
        return switch (state.direction) {
            case UP -> state.position.x() - edge.p1.x();
            case DOWN -> edge.p2.x() - state.position.x();
            case LEFT -> edge.p2.y() - state.position.y();
            case RIGHT -> state.position.y() - edge.p1.y();
        };
    }

    private static State getEnteringState(Edge edge, int index) {
        switch (edge.enterDirection) {
            case UP -> {
                return new State(new Point(edge.p1.x() + index, edge.p1.y()), Direction.UP);
            }
            case DOWN -> {
                return new State(new Point(edge.p2.x() - index, edge.p2.y()), Direction.DOWN);
            }
            case LEFT -> {
                return new State(new Point(edge.p2.x(), edge.p2.y() - index), Direction.LEFT);
            }
            case RIGHT -> {
                return new State(new Point(edge.p1.x(), edge.p1.y() + index), Direction.RIGHT);
            }
            default -> throw new RuntimeException("Oups");
        }
    }
}
