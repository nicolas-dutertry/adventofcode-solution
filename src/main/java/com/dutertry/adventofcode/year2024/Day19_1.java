package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day19_1 {

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

            for(String design : designs) {
                if(isOK(design, towels)) {
                    total++;
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isOK(String design, List<String> towels) {
        if(design.isEmpty()) {
            return true;
        }
        for (String towel : towels) {
            if(design.startsWith(towel)) {
                String remainingDesign = StringUtils.removeStart(design, towel);
                boolean ok = isOK(remainingDesign, towels);
                if(ok) {
                    return true;
                }
            }
        }
        return false;
    }
}
