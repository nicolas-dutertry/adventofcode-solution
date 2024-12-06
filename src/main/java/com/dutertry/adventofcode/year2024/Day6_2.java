package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day6_2 {
    private record State(Point pos, Direction direction) {}

    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(6));

            Point startPos = map.find('^');

            Set<Point> visitedPositions = getVisitedPositions(map, startPos);

            long count = map.stream()
                    .filter(obsPos -> !obsPos.equals(startPos))
                    .filter(visitedPositions::contains)
                    .filter(obsPos -> isLoop(map, startPos, obsPos))
                    .count();

            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Point> getVisitedPositions(AdventMap map, Point startPos) {
        Set<Point> positions = new HashSet<>();
        positions.add(startPos);

        Point pos = startPos;
        Direction direction = Direction.UP;

        while(true) {
            Point nextPos = direction.move(pos);
            Character nextChar = map.getChar(nextPos);
            if(nextChar == null) {
                break;
            } else if(nextChar == '#')  {
                direction = direction.turnRight();
            } else {
                positions.add(nextPos);
                pos = nextPos;
            }
        }
        return positions;
    }

    private static boolean isLoop(AdventMap map, Point startPos, Point obsPos) {
        Set<State> states = new HashSet<>();

        Point pos = startPos;
        Direction direction = Direction.UP;

        while(true) {
            State state = new State(pos, direction);
            if(states.contains(state)) {
                return true;
            }
            states.add(state);

            Point nextPos = direction.move(pos);
            Character nextChar = map.getChar(nextPos);
            if(nextPos.equals(obsPos)) {
                nextChar = '#';
            }

            if(nextChar == null) {
                return false;
            } else if(nextChar == '#')  {
                direction = direction.turnRight();
            } else {
                pos = nextPos;
            }
        }
    }
}
