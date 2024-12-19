package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19_2 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(19)) {
            long total = 0;
            List<String> towels = new ArrayList<>();
            List<String> designs = new ArrayList<>();

            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }

                if(towels.isEmpty()) {
                    Arrays.stream(StringUtils.split(line, ","))
                            .map(String::trim)
                            .forEach(towels::add);
                } else {
                    designs.add(line);
                }
            }

            Map<String, Long> cache = new HashMap<>();
            for(String design : designs) {
                total += countOK(design, towels, cache);
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long countOK(String design, List<String> towels, Map<String, Long> cache) {
        if(cache.containsKey(design)) {
            return cache.get(design);
        }

        long count = 0;
        for (String towel : towels) {
            if(design.startsWith(towel)) {
                String remainingDesign = StringUtils.removeStart(design, towel);
                if(StringUtils.isEmpty(remainingDesign)) {
                    count++;
                } else {
                    count += countOK(remainingDesign, towels, cache);
                }
            }
        }

        cache.put(design, count);
        return count;
    }
}
