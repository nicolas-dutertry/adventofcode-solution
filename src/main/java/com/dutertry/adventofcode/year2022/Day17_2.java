package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day17_2 {
    private static record State(int rockNumber, int jet, Set<Point> blockingPoints) {}
    private static record StateInfo(long rockIndex, int maxHeight) {}
    public static void main(String[] args) {
        try {
            long totalRocks = 1000000000000L;
            long skippedHeight = 0;

            String jetpattern = AdventUtils.getString(17);
            int jetpatternLength = jetpattern.length();
            int jetIndex = 0;
            Set<Point> restPoints = new HashSet<>();
            Map<State, StateInfo> stateMap = new HashMap<>();
            for(long i = 0; i < totalRocks; i++) {
                int rockNumber = (int)(i % 5);
                Set<Point> rock = getRock(rockNumber, restPoints);
                while(true) {
                    char jetChar = jetpattern.charAt(jetIndex % jetpatternLength);
                    jetIndex++;
                    Set<Point> nextRock;
                    if(jetChar == '<') {
                        nextRock = moveLeft(rock);
                    } else if(jetChar == '>') {
                        nextRock = moveRight(rock);
                    } else {
                        throw new RuntimeException("Invalid jet: " + jetChar);
                    }
                    if(isValid(nextRock, restPoints)) {
                        rock = nextRock;
                    }

                    nextRock = moveDown(rock);
                    if(isValid(nextRock, restPoints)) {
                        rock = nextRock;
                    } else {
                        restPoints.addAll(rock);
                        break;
                    }
                }

                if(skippedHeight == 0) {
                    int maxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);
                    State state = getState(rockNumber, jetIndex % jetpatternLength, restPoints);
                    if (stateMap.containsKey(state)) {
                        StateInfo stateInfo = stateMap.get(state);

                        long rockPeriod = i - stateInfo.rockIndex();
                        int heightDif = maxHeight - stateInfo.maxHeight();
                        long skippedIterations = (totalRocks-i-1) / rockPeriod;
                        long newIndex = i + skippedIterations * rockPeriod;
                        skippedHeight = skippedIterations * heightDif;

                        i = newIndex;
                    } else {
                        stateMap.put(state, new StateInfo(i, maxHeight));
                    }
                }
            }

            long finalMaxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0) + skippedHeight;
            System.out.println(finalMaxHeight);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Point> getRock(int rockNumber, Set<Point> restPoints) {
        int maxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);

        return switch (rockNumber) {
            case 0 -> Set.of(
                    new Point(3, maxHeight + 4),
                    new Point(4, maxHeight + 4),
                    new Point(5, maxHeight + 4),
                    new Point(6, maxHeight + 4));
            case 1 -> Set.of(new Point(4, maxHeight + 4),
                    new Point(3, maxHeight + 5),
                    new Point(4, maxHeight + 5),
                    new Point(5, maxHeight + 5),
                    new Point(4, maxHeight + 6));
            case 2 -> Set.of(
                    new Point(3, maxHeight + 4),
                    new Point(4, maxHeight + 4),
                    new Point(5, maxHeight + 4),
                    new Point(5, maxHeight + 5),
                    new Point(5, maxHeight + 6));
            case 3 -> Set.of(
                    new Point(3, maxHeight + 4),
                    new Point(3, maxHeight + 5),
                    new Point(3, maxHeight + 6),
                    new Point(3, maxHeight + 7));
            case 4 -> Set.of(
                    new Point(3, maxHeight + 4),
                    new Point(3, maxHeight + 5),
                    new Point(4, maxHeight + 4),
                    new Point(4, maxHeight + 5));
            default -> throw new RuntimeException("Invalid i: " + rockNumber);
        };
    }

    private static Set<Point> moveLeft(Set<Point> rock) {
        return rock.stream().map(p -> new Point(p.x() - 1, p.y())).collect(Collectors.toSet());
    }

    private static Set<Point> moveRight(Set<Point> rock) {
        return rock.stream().map(p -> new Point(p.x() + 1, p.y())).collect(Collectors.toSet());
    }

    private static Set<Point> moveDown(Set<Point> rock) {
        return rock.stream().map(p -> new Point(p.x(), p.y() - 1)).collect(Collectors.toSet());
    }

    private static boolean isValid(Set<Point> rock, Set<Point> restPoints) {
        for (Point point : rock) {
            if(point.x() <= 0 || point.y() <= 0) {
                return false;
            }
            if(point.x() > 7) {
                return false;
            }
            if(restPoints.contains(point)) {
                return false;
            }
        }
        return true;
    }

    private static State getState(int rockNumber, int jet, Set<Point> restPoints) {
        Set<Point> blockingPoints = new HashSet<>();
        int maxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);
        Set<Integer> abscissas = new HashSet<>(Set.of(1,2,3,4,5,6,7));
        for(int y = maxHeight; y >= 0; y--) {
            for(int x = 1; x < 8; x++) {
                if(y == 0 || restPoints.contains(new Point(x,y))) {
                    abscissas.remove(x);
                    blockingPoints.add(new Point(x, y-maxHeight));
                }
            }
            if(abscissas.isEmpty()) {
                break;
            }
        }
        return new State(rockNumber, jet, blockingPoints);
    }
}
