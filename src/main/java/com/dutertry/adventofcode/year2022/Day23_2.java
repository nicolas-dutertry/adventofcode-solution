package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day23_2 {
    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(23)) {
            Set<Point> elves = new HashSet<>();
            String line;
            int index = 0;
            while((line = br.readLine()) != null) {
                for(int x = 0; x < line.length(); x++) {
                    if(line.charAt(x) == '#') {
                        elves.add(new Point(x, index));
                    }
                }
                index++;
            }

            List<Direction> directions = new LinkedList<>();
            directions.add(Direction.NORTH);
            directions.add(Direction.SOUTH);
            directions.add(Direction.WEST);
            directions.add(Direction.EAST);
            int round = 0;
            while(true) {
                round++;
                Map<Point, Point> proposedMovesByElf = new HashMap<>();
                Map<Point, Set<Point>> proposedMovesToOrigins = new HashMap<>();
                for(Point elf : elves) {
                    Point proposedMove = proposeMove(elf, elves, directions);
                    if(proposedMove != null) {
                        proposedMovesByElf.put(elf, proposedMove);
                        Set<Point> movingElves = proposedMovesToOrigins.computeIfAbsent(proposedMove, k -> new HashSet<>());
                        movingElves.add(elf);
                    }
                }

                Set<Point> newElves = new HashSet<>();
                boolean haveMoved = false;
                for(Point elf : elves) {
                    Point proposedMove = proposedMovesByElf.get(elf);
                    if(proposedMove != null) {
                        Set<Point> movingElves = proposedMovesToOrigins.get(proposedMove);
                        if(movingElves.size() == 1) {
                            newElves.add(proposedMove);
                            haveMoved = true;
                        } else {
                            newElves.add(elf);
                        }
                    } else {
                        newElves.add(elf);
                    }
                }

                if(!haveMoved) {
                    System.out.println(round);
                    break;
                }

                elves = newElves;
                Direction firstDirection = directions.remove(0);
                directions.add(firstDirection);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(Set<Point> elves) {
        int minx = elves.stream().mapToInt(Point::x).min().orElseThrow();
        int maxx = elves.stream().mapToInt(Point::x).max().orElseThrow();
        int miny = elves.stream().mapToInt(Point::y).min().orElseThrow();
        int maxy = elves.stream().mapToInt(Point::y).max().orElseThrow();

        for(int y = miny; y <= maxy; y++) {
            for(int x = minx; x <= maxx; x++) {
                if(!elves.contains(new Point(x, y))) {
                    System.out.print('.');
                } else {
                    System.out.print('#');
                }
            }
            System.out.println();
        }
    }

    private static boolean canTryMove(Point elf, Set<Point> elves) {
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                if(x == 0 && y == 0) {
                    continue;
                }
                Point point = new Point(elf.x() + x, elf.y() + y);
                if(elves.contains(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canMove(Direction direction, Point elf, Set<Point> elves) {
        return switch (direction) {
            case NORTH -> !elves.contains(new Point(elf.x(), elf.y() - 1)) &&
                    !elves.contains(new Point(elf.x() - 1, elf.y() - 1)) &&
                    !elves.contains(new Point(elf.x() + 1, elf.y() - 1));
            case SOUTH -> !elves.contains(new Point(elf.x(), elf.y() + 1)) &&
                    !elves.contains(new Point(elf.x() - 1, elf.y() + 1)) &&
                    !elves.contains(new Point(elf.x() + 1, elf.y() + 1));
            case EAST -> !elves.contains(new Point(elf.x() + 1, elf.y())) &&
                    !elves.contains(new Point(elf.x() + 1, elf.y() - 1)) &&
                    !elves.contains(new Point(elf.x() + 1, elf.y() + 1));
            case WEST -> !elves.contains(new Point(elf.x() - 1, elf.y())) &&
                    !elves.contains(new Point(elf.x() - 1, elf.y() - 1)) &&
                    !elves.contains(new Point(elf.x() - 1, elf.y() + 1));
            default -> throw new RuntimeException("Invalid direction: " + direction);
        };
    }

    private static Point proposeMove(Point elf, Set<Point> elves, List<Direction> directions) {
        if(!canTryMove(elf, elves)) {
            return null;
        }

        for (Direction direction : directions) {
            if(canMove(direction, elf, elves)) {
                return switch (direction) {
                    case NORTH -> new Point(elf.x(), elf.y() - 1);
                    case SOUTH -> new Point(elf.x(), elf.y() + 1);
                    case EAST -> new Point(elf.x() + 1, elf.y());
                    case WEST -> new Point(elf.x() - 1, elf.y());
                    default -> throw new RuntimeException("Invalid direction: " + direction);
                };
            }
        }

        return null;
    }
}
