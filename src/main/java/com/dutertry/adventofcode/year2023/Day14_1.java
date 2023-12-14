package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day14_1 {
    public static void main(String[] args) {
        try {
            List<StringBuilder> lines = AdventUtils.getStringBuilderLines(14);
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
            long totalLoad = 0;
            for (int i = 0; i < lines.size(); i++) {
                StringBuilder line = lines.get(i);
                int rocks = StringUtils.countMatches(line, 'O');
                int lineLoad = lines.size()-i;
                long load = lineLoad*rocks;
                totalLoad += load;
            }
            System.out.println(totalLoad);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
