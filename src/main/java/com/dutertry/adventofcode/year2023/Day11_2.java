package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Day11_2 {
    private static class Galaxie {
        private final int x;
        private final int y;
        
        public Galaxie(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> map = AdventUtils.getLines(11);
            Set<Integer> emptyRows = new HashSet<>();
            Set<Integer> emptyColumns = new HashSet<>();
            
            for (int i = 0; i < map.size(); i++) {
                String line = map.get(i);
                if(StringUtils.containsOnly(line, '.')) {
                    emptyRows.add(i);
                }
            }
            String firstLine = map.get(0);
            for (int j = 0; j < firstLine.length(); j++) {
                boolean empty = true;
                for (int i = 0; i < map.size(); i++) {
                    String line = map.get(i);
                    if(line.charAt(j) != '.') {
                        empty = false;
                        break;
                    }
                }
                if(empty) {
                    emptyColumns.add(j);
                }
            }
            
            List<Galaxie> galaxies = new ArrayList<>();
            for (int y = 0; y < map.size(); y++) {
                String line = map.get(y);
                for(int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if(c == '#') {
                        galaxies.add(new Galaxie(x, y));
                    }
                }
            }
            
            long total = 0;
            for (int i = 0; i < galaxies.size(); i++) {
                Galaxie galaxie1 = galaxies.get(i);
                for (int j = i+1; j < galaxies.size(); j++) {
                    Galaxie galaxie2 = galaxies.get(j);
                    int x1 = galaxie1.x;
                    int x2 = galaxie2.x;
                    if(x1 > x2) {
                        x1 = galaxie2.x;
                        x2 = galaxie1.x;
                    }
                    int y1 = galaxie1.y;
                    int y2 = galaxie2.y;
                    if(y1 > y2) {
                        y1 = galaxie2.y;
                        y2 = galaxie1.y;
                    }
                    
                    long distance = x2-x1 + y2-y1;
                    
                    for(int col = x1+1; col < x2; col++) {
                        if(emptyColumns.contains(col)) {
                            distance += 1000000-1;
                        }
                    }
                    
                    for(int row = y1+1; row < y2; row++) {
                        if(emptyRows.contains(row)) {
                            distance += 1000000-1;
                        }
                    }
                    
                    total += distance;
                }
            }
            System.out.println(total);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
