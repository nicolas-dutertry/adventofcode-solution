package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_1 {
    private static final int xSize = 101;
    private static final int ySize = 103;
    private static final Pattern PATTERN = Pattern.compile("[0-9\\-]+");

    private static record Robot(Point position, Point speed) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(14)) {
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


            List<Robot> newRobots = new LinkedList<>();
            for (Robot robot : robots) {
                Robot newRobot = moveRobot(robot, 100);
                newRobots.add(newRobot);
            }
            robots = newRobots;

            int q1 = 0;
            int q2 = 0;
            int q3 = 0;
            int q4 = 0;
            for (Robot robot : robots) {
                Point position = robot.position();
                if(position.x() < xSize / 2 && position.y() < ySize / 2) {
                    q1++;
                } else if(position.x() > xSize / 2 && position.y() < ySize / 2) {
                    q2++;
                } else if(position.x() < xSize / 2 && position.y() > ySize / 2) {
                    q3++;
                } else if(position.x() > xSize / 2 && position.y() > ySize / 2) {
                    q4++;
                }
            }

            long total = (long)q1 * q2 * q3 * q4;

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Robot moveRobot(Robot robot, int times) {
        int newX = robot.position().x() + robot.speed().x() * times;
        int newY = robot.position().y() + robot.speed().y() * times;
        int xMod = Math.floorMod(newX, xSize);
        int yMod = Math.floorMod(newY, ySize);
        Point newPosition = new Point(xMod, yMod);
        return new Robot(newPosition, robot.speed());
    }
}
