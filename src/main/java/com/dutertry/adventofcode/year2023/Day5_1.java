package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day5_1 {
    private static class Range {
        public long destStart;
        public long sourceStart;
        public long lenght;
        
        public Range(long destStart, long sourceStart, long lenght) {
            this.destStart = destStart;
            this.sourceStart = sourceStart;
            this.lenght = lenght;
        }        
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
            
            List<Long> seeds = null;
            List<List<Range>> allRanges = new LinkedList<>();
            List<Range> currentRanges = null;
            
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }
                
                if(seeds == null) {
                    seeds = new LinkedList<>();
                    String[] array = StringUtils.split(line);
                    for(int i = 1; i < array.length; i++) {
                        seeds.add(Long.parseLong(array[i].trim()));
                    }
                } else {
                    if(line.contains("map")) {
                        currentRanges = new LinkedList<>();
                        allRanges.add(currentRanges);
                    } else {
                        String[] array = StringUtils.split(line);
                        currentRanges.add(new Range(Long.parseLong(array[0].trim()), Long.parseLong(array[1].trim()), Long.parseLong(array[2].trim())));
                    }
                }
            }
            
            long minLocation = Long.MAX_VALUE; 
            for (long seed : seeds) {
                long source = seed;
                
                for (List<Range> ranges : allRanges) {
                    long dest = source;
                    for (Range range : ranges) {
                        if(source >= range.sourceStart && source < range.sourceStart+range.lenght) {
                            dest = range.destStart + source - range.sourceStart;
                            break;
                        }
                    }
                    source = dest;
                }
                
                if(source < minLocation) {
                    minLocation = source;
                }
            }
            System.out.println(minLocation);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
