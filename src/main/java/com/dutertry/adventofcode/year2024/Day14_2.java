package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_2 {
    private static final int xSize = 101;
    private static final int ySize = 103;
    private static final Pattern PATTERN = Pattern.compile("[0-9\\-]+");

    private static record Robot(Point position, Point speed) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(14)) {
            long total = 0;
            String line;
            List<Robot> robots = new LinkedList<>();
            while((line = br.readLine()) != null ) {
                Matcher matcher = PATTERN.matcher(line);
                List<Integer> values = matcher.results().map(m -> Integer.parseInt(m.group())).toList();
                Robot robot = new Robot(
                        new Point(values.get(0), values.get(1)),
                        new Point(values.get(2), values.get(3)));
                robots.add(robot);
            }

            int time = 1;
            while(true) {
                Set<Point> positions = new HashSet<>();
                List<Robot> newRobots = new LinkedList<>();
                for (Robot robot : robots) {
                    Point newPosition = robot.position().add(robot.speed());
                    int xMod = Math.floorMod(newPosition.x(), xSize);
                    int yMod = Math.floorMod(newPosition.y(), ySize);
                    newPosition = new Point(xMod, yMod);
                    positions.add(newPosition);
                    Robot newRobot = new Robot(newPosition, robot.speed());
                    newRobots.add(newRobot);
                }
                robots = newRobots;
                if(countPeaks(positions, true) > 1 && countPeaks(positions, false) > 1) {
                    print(System.out, robots, time);
                    break;
                }
                time++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int countPeaks(Set<Point> points, boolean right)  {
        int count = 0;
        for (Point point : points) {
            boolean found = true;
            main : for(int i = 1; i < 3; i++) {
                int x = right ? i : - i;
                for(int j = 0; j < 2; j++) {
                    if (!points.contains(new Point(point.x() + x, point.y() + x*j))) {
                        found = false;
                        break main;
                    }
                }
            }
            if(found) {
                count++;
            }
        }
        return count;
    }

    private static void print(PrintStream ps, List<Robot> robots, int time)  {
        ps.println("Time: " + time);
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                Point point = new Point(x, y);
                if(robots.stream().anyMatch(r -> r.position().equals(point))) {
                    ps.print("#");
                } else {
                    ps.print(".");
                }
            }
            ps.println();
        }
    }
}
