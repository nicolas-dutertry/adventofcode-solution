package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day6_1 {
    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(6));

            Point startPos = map.find('^');

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

            System.out.println(positions.size());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
