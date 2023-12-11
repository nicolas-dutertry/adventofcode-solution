package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class AdventUtils {

    public static BufferedReader getBufferedReader(int day) {
        return new BufferedReader(new InputStreamReader(AdventUtils.class.getResourceAsStream("input" + day + ".txt"), StandardCharsets.UTF_8));
    }
    
    public static List<String> getLines(int day) throws IOException {
        List<String> lines = new LinkedList<>();
        try(BufferedReader br = getBufferedReader(day)) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
