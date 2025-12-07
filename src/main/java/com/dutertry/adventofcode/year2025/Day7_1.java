package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7_1 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(7);
            AdventMap map = new AdventMap(lines);
            Point start = map.find('S');
            Set<Point> tachyons = new HashSet<>();
            tachyons.add(start);
            long splits = 0;
            main : while(true) {
                Set<Point> nexts = new HashSet<>();
                for(Point tachyon : tachyons) {
                    Point down = Direction.DOWN.move(tachyon);
                    Character downChar = map.getChar(down);
                    if(downChar == null) {
                        break main;
                    }
                    if(downChar == '.') {
                        nexts.add(down);
                    } else if(downChar == '^') {
                        nexts.add(Direction.LEFT.move(down));
                        nexts.add(Direction.RIGHT.move(down));
                        splits++;
                    } else {
                        throw new RuntimeException("oups");
                    }
                }
                tachyons = nexts;
            }
            System.out.println(splits);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
