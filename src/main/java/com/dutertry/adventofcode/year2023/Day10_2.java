package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Day10_2 {
    private static class Point {
        public final int x;
        public final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }
    
    public static void main(String[] args) {
        List<String> map = new LinkedList<>();
        Collection<Point> loop = new HashSet<>();
        try (BufferedReader br = AdventUtils.getBufferedReader(10)) {
            String line;
            int sx = 0;
            int sy = 0;
            char sc = 'S';
            int i = 0;
            while ((line = br.readLine()) != null) {
                map.add(line);
                if(line.contains("S")) {
                    sy = i;
                    sx = line.indexOf('S');
                }
                i++;
            }
            loop.add(new Point(sx, sy));
            
            int northx = sx;
            int northy = sy-1;
            char northChar = map.get(northy).charAt(northx);
            int southx = sx;
            int southy = sy+1;
            char southChar = map.get(southy).charAt(southx);
            int eastx = sx+1;
            int easty = sy;
            char eastChar = map.get(easty).charAt(eastx);
            int westx = sx-1;
            int westy = sy;
            char westChar = map.get(westy).charAt(westx);
            
            char currentChar = 'S';
            int x = 0;
            int y = 0;
            
            boolean north=false, south=false, east=false, west=false;
            
            if(northChar == 'F' || northChar == '|' || northChar == '7') {
                currentChar = northChar;
                x = northx;
                y = northy;
                north = true;
            }
            if(southChar == 'J' || southChar == '|' || southChar == 'L') {
                currentChar = southChar;
                x = southx;
                y = southy;
                south = true;
            }
            if(eastChar == 'J' || eastChar == '-' || eastChar == '7') {
                currentChar = eastChar;
                x = eastx;
                y = easty;
                east = true;
            }
            if(westChar == 'L' || eastChar == '-' || eastChar == 'F') {
                currentChar = westChar;
                x = westx;
                y = westy;
                west = true;
            }
            
            if(north && south) {
                sc = '|';
            } else if(north && east) {
                sc = 'L';
            } else if(north && west) {
                sc = 'J';
            } else if(east && west) {
                sc = '-';
            } else if(south && east) {
                sc = 'F';
            } else if(south && west) {
                sc = '7';
            }
            
            char prevChar = 'S';
            int prevx = sx;
            int prevy = sy;
            while(true) {
                loop.add(new Point(x, y));
                northx = x;
                northy = y-1;
                southx = x;
                southy = y+1;
                eastx = x+1;
                easty = y;
                westx = x-1;
                westy = y;
                
                int nextx;
                int nexty;
                switch (currentChar) {
                case '|': {
                    if((northx != prevx || northy != prevy)) {
                        nextx = northx;
                        nexty = northy;
                    } else {
                        nextx = southx;
                        nexty = southy;
                    }
                    break;
                }
                case '-': {
                    if(eastx != prevx || easty != prevy) {
                        nextx = eastx;
                        nexty = easty;
                    } else {
                        nextx = westx;
                        nexty = westy;
                    }
                    break;
                }
                case 'L': {
                    if(northx != prevx || northy != prevy) {
                        nextx = northx;
                        nexty = northy;
                    } else {
                        nextx = eastx;
                        nexty = easty;
                    }
                    break;
                }
                case 'J': {
                    if(northx != prevx || northy != prevy) {
                        nextx = northx;
                        nexty = northy;
                    } else {
                        nextx = westx;
                        nexty = westy;
                    }
                    break;
                }
                case '7': {
                    if(southx != prevx || southy != prevy) {
                        nextx = southx;
                        nexty = southy;
                    } else {
                        nextx = westx;
                        nexty = westy;
                    }
                    break;
                }
                case 'F': {
                    if(southx != prevx || southy != prevy) {
                        nextx = southx;
                        nexty = southy;
                    } else {
                        nextx = eastx;
                        nexty = easty;
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unexpected value: " + prevChar);
                }
                
                char nextChar = map.get(nexty).charAt(nextx);
                if(nextChar == 'S') {
                    break;
                }
                prevChar = currentChar;
                prevx = x;
                prevy = y;
                currentChar = nextChar;
                x = nextx;
                y = nexty;
            }
            
            int inLoop = 0;
            for(int Y = 0; Y < map.size(); Y++) {
                String mapLine = map.get(Y);
                for(int X = 0; X < mapLine.length(); X++) {
                    if(isInLoop(X, Y, loop, map, sc)) {
                        inLoop++;
                    }
                }
            }
            System.out.println(inLoop);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isInLoop(int x, int y, Collection<Point> loop, List<String> map, char sc) {
        if(loop.contains(new Point(x, y))) {
            return false;
        }
        
        int intersections = 0;
        for(int i = y-1; i >=0; i--) {
            if(loop.contains(new Point(x, i))) {
                char c = map.get(i).charAt(x);
                if(c == 'S') {
                    c = sc;
                }
                if(c != '|' && c != 'F' && c != 'L') {
                    intersections++;
                }
            }
        }
        return intersections % 2 == 1;
    }

}
