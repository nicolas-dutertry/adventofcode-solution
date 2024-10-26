package com.dutertry.adventofcode.year2022;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day22_1 {

    private static record Line(int offset, String content) {}

    public static void main(String[] args) {
        try {
            List<String> stringLines = AdventUtils.getLines(22);
            String path = stringLines.remove(stringLines.size()-1);
            List<Object> moves = parsePath(path);

            stringLines.remove(stringLines.size()-1);

            int length = stringLines.stream().mapToInt(String::length).max().orElse(0);
            stringLines = stringLines.stream().map(s -> StringUtils.rightPad(s, length)).toList();

            List<String> invertedStringLines = new LinkedList<>();
            for(int i = 0; i < length; i++) {
                StringBuilder sb = new StringBuilder();
                for(String line : stringLines) {
                    sb.append(line.charAt(i));
                }
                invertedStringLines.add(sb.toString());
            }

            List<Line> lines = stringLines.stream().map(Day22_1::convert).toList();
            List<Line> invertedLines = invertedStringLines.stream().map(Day22_1::convert).toList();

            int y = 0;
            int x = lines.get(0).offset();

            char direction = '>';
            Point position = new Point(x, y);

            for(Object move : moves) {
                if(move instanceof Character) {
                    direction = changeDirection(direction, (char)move);
                } else {
                    int steps = (int)move;
                    position = move(lines, invertedLines, position, direction, steps);
                }
            }

            //Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^)
            int directionScore = 0;
            if(direction == 'v') {
                directionScore = 1;
            } else if(direction == '<') {
                directionScore = 2;
            } else if(direction == '^') {
                directionScore = 3;
            }

            int res = 1000*(position.y()+1) + 4*(position.x()+1) + directionScore;
            System.out.println(res);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object> parsePath(String path) {
        List<Object> moves = new LinkedList<>();
        String current = "";
        for(int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if(c == 'R' || c == 'L') {
                if(!current.isEmpty()) {
                    moves.add(Integer.parseInt(current));
                }
                current = "";
                moves.add(c);
            } else {
                current += c;
            }
        }

        if(!current.isEmpty()) {
            moves.add(Integer.parseInt(current));
        }
        return moves;
    }

    private static char changeDirection(char direction, char move) {
        if(move == 'R') {
            switch(direction) {
                case '^': return '>';
                case '>': return 'v';
                case 'v': return '<';
                case '<': return '^';
            }
        } else {
            switch(direction) {
                case '^': return '<';
                case '>': return '^';
                case 'v': return '>';
                case '<': return 'v';
            }
        }
        return direction;
    }

    private static Point move(List<Line> lines, List<Line> invertedLines, Point position, char direction, int steps) {

        switch(direction) {
            case '^': {
                Line invertedLine = invertedLines.get(position.x());
                int index = position.y() - invertedLine.offset();
                for (int i = 0; i < steps; i++) {
                    int nextIndex = index - 1;
                    if (nextIndex < 0) {
                        nextIndex = invertedLine.content().length() - 1;
                    }
                    char c = invertedLine.content().charAt(nextIndex);
                    if (c == '.') {
                        index = nextIndex;
                    } else if (c == '#') {
                        break;
                    } else {
                        throw new RuntimeException("Oups");
                    }
                }
                return new Point(position.x(), index + invertedLine.offset());
            }
            case '>': {
                Line line = lines.get(position.y());
                int index = position.x() - line.offset();
                for (int i = 0; i < steps; i++) {
                    int nextIndex = index + 1;
                    if (nextIndex >= line.content().length()) {
                        nextIndex = 0;
                    }
                    char c = line.content().charAt(nextIndex);
                    if (c == '.') {
                        index = nextIndex;
                    } else if (c == '#') {
                        break;
                    } else {
                        throw new RuntimeException("Oups");
                    }
                }
                return new Point(index + line.offset(), position.y());
            }
            case 'v': {
                Line invertedLine = invertedLines.get(position.x());
                int index = position.y() - invertedLine.offset();
                for (int i = 0; i < steps; i++) {
                    int nextIndex = index + 1;
                    if (nextIndex >= invertedLine.content().length()) {
                        nextIndex = 0;
                    }
                    char c = invertedLine.content().charAt(nextIndex);
                    if (c == '.') {
                        index = nextIndex;
                    } else if (c == '#') {
                        break;
                    } else {
                        throw new RuntimeException("Oups");
                    }
                }
                return new Point(position.x(), index + invertedLine.offset());
            }
            case '<': {
                Line line = lines.get(position.y());
                int index = position.x() - line.offset();
                for (int i = 0; i < steps; i++) {
                    int nextIndex = index - 1;
                    if (nextIndex < 0) {
                        nextIndex = line.content().length() - 1;
                    }
                    char c = line.content().charAt(nextIndex);
                    if (c == '.') {
                        index = nextIndex;
                    } else if (c == '#') {
                        break;
                    } else {
                        throw new RuntimeException("Oups");
                    }
                }
                return new Point(index + line.offset(), position.y());
            }
        }
        return position;
    }

    private static Line convert(String line) {
        int offset = 0;
        while(line.charAt(offset) == ' ') {
            offset++;
        }
        return new Line(offset, line.trim());
    }
}
