package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day15_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(15)) {
            long total = 0;

            String line;
            List<String> lines = new LinkedList<>();
            StringBuilder moves = new StringBuilder();
            boolean isMove = false;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    isMove = true;
                    continue;
                }

                if(!isMove) {
                    lines.add(line);
                } else {
                    moves.append(line);
                }
            }


            AdventMap map = new AdventMap(lines);

            Point robotPoint = map.find('@');

            for(int i = 0; i < moves.length(); i++) {
                char dc = moves.charAt(i);
                Direction direction = Direction.fromSymbol(dc);
                Point point = direction.move(robotPoint);
                if(map.getChar(point) == '.') {
                    map.setChar(point, '@');
                    map.setChar(robotPoint, '.');
                    robotPoint = point;
                    continue;
                }

                if(map.getChar(point) == '#') {
                    continue;
                }

                List<Point> boxes = new LinkedList<>();
                Point nextPoint = point;
                while(map.getChar(nextPoint) == 'O') {
                    boxes.add(nextPoint);
                    nextPoint = direction.move(nextPoint);
                }

                if(map.getChar(nextPoint) == '.') {
                    for (Point box : boxes) {
                        map.setChar(direction.move(box), 'O');
                    }
                    map.setChar(point, '@');
                    map.setChar(robotPoint, '.');
                    robotPoint = point;
                }
            }

            for(int y = 0; y < map.getYSize(); y++) {
                for(int x = 0; x < map.getXSize(); x++) {
                    Point point = new Point(x, y);
                    char c = map.getChar(point);
                    if(c == 'O') {
                        total += 100L*y + x;
                    }
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
