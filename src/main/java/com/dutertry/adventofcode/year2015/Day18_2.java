package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day18_2 {
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(18);
            String firstLine = lines.get(0);
            firstLine = "#" + firstLine.substring(1, firstLine.length()-1) + "#";
            lines.set(0, firstLine);
            String lastLine = lines.get(lines.size()-1);
            lastLine = "#" + lastLine.substring(1, lastLine.length()-1) + "#";
            lines.set(lines.size()-1, lastLine);
            
            for(int step = 0; step < 100; step++) {
                List<String> newLines = new LinkedList<>();
                for(int y = 0; y < lines.size(); y++) {
                    String line = lines.get(y);
                    StringBuilder newLine = new StringBuilder();
                    for(int x = 0; x < line.length(); x++) {
                        if((x == 0 && (y == 0 || y == lines.size()-1)) || (x == line.length()-1 && (y == 0 || y == lines.size()-1))) {
                            newLine.append('#');
                        } else {
                            char c = line.charAt(x);
                            int onNeighbors = countOnNeighbors(x, y, lines);
                            if(c == '#' && (onNeighbors == 2 || onNeighbors == 3)) {
                                newLine.append('#');
                            } else if(c == '.' && onNeighbors == 3) {
                                newLine.append('#');
                            } else {
                                newLine.append('.');
                            }
                        }
                    }
                    newLines.add(newLine.toString());
                }
                lines = newLines;
            }
            
            int onLights = 0;
            for (String line : lines) {
                onLights += StringUtils.countMatches(line, '#');
            }
            System.out.println(onLights);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    private static int countOnNeighbors(int x, int y, List<String> lines) {
        int count = 0;
        if(y < lines.size()-1) {
            String bottomLine = lines.get(y+1); 
            if(x > 0 && bottomLine.charAt(x-1) == '#') {
                count++;
            }
            if(bottomLine.charAt(x) == '#') {
                count++;
            }
            if(x < bottomLine.length()-1 && bottomLine.charAt(x+1) == '#') {
                count++;
            }
        }
        String line = lines.get(y); 
        if(x > 0 && line.charAt(x-1) == '#') {
            count++;
        }
        if(x < line.length()-1 && line.charAt(x+1) == '#') {
            count++;
        }
        if(y > 0) {
            String topLine = lines.get(y-1); 
            if(x > 0 && topLine.charAt(x-1) == '#') {
                count++;
            }
            if(topLine.charAt(x) == '#') {
                count++;
            }
            if(x < topLine.length()-1 && topLine.charAt(x+1) == '#') {
                count++;
            }
        }
        return count;
    }
}
