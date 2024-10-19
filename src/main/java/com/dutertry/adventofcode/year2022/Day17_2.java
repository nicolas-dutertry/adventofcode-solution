package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day17_2 {

    public static void main(String[] args) {
        try {
            long totalRocks = 1000000000000L;
            //int totalRocks = 5600; -> 8827
            //long totalRocks = 5600L;

            long l = totalRocks - 3719;
            long l2 = 5439-3719;
            long h2 = 8564-5860;
            long n = l / l2;
            long rest = l % l2;
            int max = 3719 + (int)rest;

            String jetpattern = AdventUtils.getString(17);
            int jetpatternLength = jetpattern.length();
            int jetIndex = 0;
            Set<Point> restPoints = new HashSet<>();
            int maxHeight = 0;
            int[] serie = new int[1000];
            int[] currentserie = new int[1000];
            for(int i = 0; i < max; i++) {
                Set<Point> rock = getRock(i, restPoints);
                while(true) {
                    char jet = jetpattern.charAt(jetIndex % jetpatternLength);
                    jetIndex++;
                    Set<Point> nextRock;
                    if(jet == '<') {
                        nextRock = moveLeft(rock);
                    } else if(jet == '>') {
                        nextRock = moveRight(rock);
                    } else {
                        throw new RuntimeException("Invalid jet: " + jet);
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
                int newMaxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);
                if(i >= 1000 && i < 2000) {
                    serie[i - 1000] = newMaxHeight-maxHeight;
                }
                if(i >= 2000) {
                    for(int j = 0; j < currentserie.length-1; j++) {
                        currentserie[j] = currentserie[j+1];
                    }
                    currentserie[currentserie.length-1] = newMaxHeight-maxHeight;

                    if(Arrays.equals(serie, currentserie)) {
                        System.out.println("Found serie at i=" + i + " with height=" + newMaxHeight);
                    }
                }
                maxHeight = newMaxHeight;
            }


            /*
            for(int y = maxHeight; y > 0; y--) {
                System.out.print("|");
                for(int x = 1; x < 8; x++) {
                    if(restPoints.contains(new Point(x, y))) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println("|");
            }
            System.out.println("+-------+");
            */
            int finalmaxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);
            System.out.println(finalmaxHeight);
            System.out.println(finalmaxHeight+n*h2);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Point> getRock(int i, Set<Point> restPoints) {
        int maxHeight = restPoints.stream().mapToInt(p -> p.y()).max().orElse(0);

        return switch (i % 5) {
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
            default -> throw new RuntimeException("Invalid i: " + i);
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
}
