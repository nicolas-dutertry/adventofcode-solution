package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day14_2 {
    private static final int CYCLES = 1000000000;
    public static void main(String[] args) {
        try {
            List<StringBuilder> lines = AdventUtils.getStringBuilderLines(14);
            List<List<StringBuilder>> copies = new ArrayList<>();
            int period = -1;
            int startPeriodIdx = -1;
            for(int j = 0; j < CYCLES; j++) {
                moveNorth(lines);
                moveWest(lines);
                moveSouth(lines);
                moveEast(lines);
                
                startPeriodIdx = indexOf(copies, lines);
                if(startPeriodIdx != -1) {
                    period = j-startPeriodIdx;
                    break;
                }
                copies.add(copy(lines));
            }
            
            int idx = startPeriodIdx + ((CYCLES-startPeriodIdx-1) % period);            
            System.out.println(getLoad(copies.get(idx)));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long getLoad(List<StringBuilder> lines) {
        long totalLoad = 0;
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder line = lines.get(i);
            int rocks = StringUtils.countMatches(line, 'O');
            int lineLoad = lines.size()-i;
            long load = lineLoad*rocks;
            totalLoad += load;
        }
        return totalLoad;
    }
    
    private static void moveNorth(List<StringBuilder> lines) {
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder line = lines.get(i);
            for(int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if(c == 'O') {
                    StringBuilder newLine = null;
                    for(int k = i-1; k >= 0; k--) {
                        StringBuilder northLine = lines.get(k);
                        char cNorth = northLine.charAt(j);
                        if(cNorth == '.') {
                            newLine = northLine;
                        } else {
                            break;
                        }
                    }
                    if(newLine != null) {
                        newLine.setCharAt(j, c);
                        line.setCharAt(j, '.');
                    }
                }
            }
        }
    }
    
    private static void moveSouth(List<StringBuilder> lines) {
        for (int i = lines.size()-1; i >= 0; i--) {
            StringBuilder line = lines.get(i);
            for(int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if(c == 'O') {
                    StringBuilder newLine = null;
                    for(int k = i+1; k < lines.size(); k++) {
                        StringBuilder southLine = lines.get(k);
                        char cSouth = southLine.charAt(j);
                        if(cSouth == '.') {
                            newLine = southLine;
                        } else {
                            break;
                        }
                    }
                    if(newLine != null) {
                        newLine.setCharAt(j, c);
                        line.setCharAt(j, '.');
                    }
                }
            }
        }
    }
    
    private static void moveEast(List<StringBuilder> lines) {
        for (StringBuilder line : lines) {
            for(int i = line.length()-1; i >=0; i--) {
                char c = line.charAt(i);
                if(c == 'O') {
                    Integer newPos = null;
                    for(int j = i+1; j < line.length(); j++) {
                        char cEast = line.charAt(j);
                        if(cEast == '.') {
                            newPos = j;
                        } else {
                            break;
                        }
                    }
                    if(newPos != null) {
                        line.setCharAt(newPos, c);
                        line.setCharAt(i, '.');
                    }
                }
            }
        }
    }
    
    private static void moveWest(List<StringBuilder> lines) {
        for (StringBuilder line : lines) {
            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if(c == 'O') {
                    Integer newPos = null;
                    for(int j = i-1; j >= 0; j--) {
                        char cWest = line.charAt(j);
                        if(cWest == '.') {
                            newPos = j;
                        } else {
                            break;
                        }
                    }
                    if(newPos != null) {
                        line.setCharAt(newPos, c);
                        line.setCharAt(i, '.');
                    }
                }
            }
        }
    }
    
    private static List<StringBuilder> copy(List<StringBuilder> lines) {
        List<StringBuilder> copy = new ArrayList<>();
        for (StringBuilder line : lines) {
            copy.add(new StringBuilder(line));
        }
        return copy;
    }
    
    private static boolean equals(List<StringBuilder> l1, List<StringBuilder> l2) {
        for(int i = 0; i < l1.size(); i++) {
            StringBuilder s1 = l1.get(i);
            StringBuilder s2 = l2.get(i);
            for(int j = 0; j < s1.length(); j++) {
                if(s1.charAt(j) != s2.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static int indexOf(List<List<StringBuilder>> copies, List<StringBuilder> lines) {
        for (int i = 0; i < copies.size(); i++) {
            List<StringBuilder> copy = copies.get(i);
            if(equals(copy, lines)) {
                return i;
            }
        }
        return -1;
    }
    
}
