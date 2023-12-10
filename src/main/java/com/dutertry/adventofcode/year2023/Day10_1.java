package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day10_1 {
    public static void main(String[] args) {
        List<String> map = new LinkedList<>();
        try (BufferedReader br = AdventUtils.getBufferedReader(10)) {
            String line;
            int sx = 0;
            int sy = 0;
            int i = 0;
            while ((line = br.readLine()) != null) {
                map.add(line);
                if(line.contains("S")) {
                    sy = i;
                    sx = line.indexOf('S');
                }
                i++;
            }
            
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
            
            char currentChar;
            int x;
            int y;
            
            if(northChar == 'F' || northChar == '|' || northChar == '7') {
                currentChar = northChar;
                x = northx;
                y = northy;
            } else if(southChar == 'J' || southChar == '|' || southChar == 'L') {
                currentChar = southChar;
                x = southx;
                y = southy;
            } else if(eastChar == 'J' || eastChar == '-' || eastChar == '7') {
                currentChar = eastChar;
                x = eastx;
                y = easty;
            } else if(westChar == 'L' || eastChar == '-' || eastChar == 'F') {
                currentChar = westChar;
                x = westx;
                y = westy;
            } else {
                throw new RuntimeException("Oups");
            }
            
            int step = 1;
            char prevChar = 'S';
            int prevx = sx;
            int prevy = sy;
            while(true) {
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
                step++;
            }
            System.out.println(1+step/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
