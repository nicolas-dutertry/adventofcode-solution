package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day11_1 {
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
            List<StringBuilder> expendedMap = new LinkedList<>();
            for (String line : map) {
                expendedMap.add(new StringBuilder(line));
                if(StringUtils.containsOnly(line, ".")) {
                    expendedMap.add(new StringBuilder(line));    
                }
            }
            
            int idx = 0;
            while(true) {
                boolean empty = true;
                for (StringBuilder s : expendedMap) {
                    if(s.charAt(idx) != '.') {
                        empty = false;
                        break;
                    }
                }
                
                if(empty) {
                    for (StringBuilder s : expendedMap) {
                        s.insert(idx, '.');
                    }
                    idx++;
                }
                idx++;
                if(idx >= expendedMap.get(0).length()) {
                    break;
                }
            }
            
            List<Galaxie> galaxies = new ArrayList<>();
            for (int y = 0; y < expendedMap.size(); y++) {
                StringBuilder line = expendedMap.get(y);
                for(int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if(c == '#') {
                        galaxies.add(new Galaxie(x, y));
                    }
                }
            }
            
            int total = 0;
            for (int i = 0; i < galaxies.size(); i++) {
                Galaxie galaxie1 = galaxies.get(i);
                for (int j = i+1; j < galaxies.size(); j++) {
                    Galaxie galaxie2 = galaxies.get(j);
                    int distance = Math.abs(galaxie2.x-galaxie1.x) + Math.abs(galaxie2.y-galaxie1.y);
                    total += distance;
                }
            }
            System.out.println(total);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
