package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day9_2 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(9)) {
            final Point startPos = new Point(0,0);
            Point[] rope = new Point[10];
            for(int i = 0; i < 10; i++) {
                rope[i] = startPos;
            }
            Set<Point> tailPositions = new HashSet<>();
            tailPositions.add(startPos);
            String line;
            while((line = br.readLine()) != null) {
                char move = line.charAt(0);
                int count = Integer.parseInt(line.substring(2));
                for(int i = 0; i < count; i++) {
                    rope[0] = getNextHeadPos(rope[0], move);
                    for(int j = 0; j < 9; j++) {
                        rope[j+1] = getNextTailPos(rope[j], rope[j+1]);
                    }
                    tailPositions.add(rope[9]);
                }
            }
            System.out.println(tailPositions.size());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Point getNextHeadPos(Point headPos, char move) {
        return switch(move) {
            case 'D' -> new Point(headPos.x(), headPos.y() - 1);
            case 'U' -> new Point(headPos.x(), headPos.y() + 1);
            case 'L' -> new Point(headPos.x() - 1, headPos.y());
            case 'R' -> new Point(headPos.x() + 1, headPos.y());
            default -> throw new IllegalArgumentException("Invalid move: " + move);
        };
    }

    private static Point getNextTailPos(Point headPos, Point tailPos) {
        if(tailPos.y() == headPos.y() + 2 && tailPos.x() == headPos.x() + 2) {
            return new Point(headPos.x()+1, headPos.y()+1);
        }
        if(tailPos.y() == headPos.y() + 2 && tailPos.x() == headPos.x() - 2) {
            return new Point(headPos.x()-1, headPos.y()+1);
        }
        if(tailPos.y() == headPos.y() - 2 && tailPos.x() == headPos.x() + 2) {
            return new Point(headPos.x()+1, headPos.y()-1);
        }
        if(tailPos.y() == headPos.y() - 2 && tailPos.x() == headPos.x() - 2) {
            return new Point(headPos.x()-1, headPos.y()-1);
        }

        if(tailPos.y() == headPos.y() + 2) {
            return new Point(headPos.x(), headPos.y()+1);
        }
        if(tailPos.y() == headPos.y() - 2) {
            return new Point(headPos.x(), headPos.y()-1);
        }
        if(tailPos.x() == headPos.x() + 2) {
            return new Point(headPos.x()+1, headPos.y());
        }
        if(tailPos.x() == headPos.x() - 2) {
            return new Point(headPos.x()-1, headPos.y());
        }
        return tailPos;
    }
}
