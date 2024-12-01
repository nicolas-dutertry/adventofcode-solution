package com.dutertry.adventofcode.year2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AdventUtils {

    public static BufferedReader getBufferedReader(int day) {
        return getBufferedReader(day, false);
    }
    
    public static BufferedReader getBufferedReader(int day, boolean sample) {
        String name = "input" + day;
        if(sample) {
            name += "-e";
        }
        name += ".txt";
        return new BufferedReader(new InputStreamReader(AdventUtils.class.getResourceAsStream(name), StandardCharsets.UTF_8));
    }
    
    public static List<String> getLines(int day, boolean sample) throws IOException {
        List<String> lines = new ArrayList<>();
        try(BufferedReader br = getBufferedReader(day, sample)) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    
    public static List<String> getLines(int day) throws IOException {
        return getLines(day, false);
    }
    
    public static List<StringBuilder> getStringBuilderLines(int day) throws IOException {
        return getStringBuilderLines(day, false);
    }
    
    public static List<StringBuilder> getStringBuilderLines(int day, boolean sample) throws IOException {
        List<StringBuilder> lines = new ArrayList<>();
        try(BufferedReader br = getBufferedReader(day, sample)) {
            String line;
            while((line = br.readLine()) != null) {
                lines.add(new StringBuilder(line));
            }
        }
        return lines;
    }
    
    public static String getString(int day, boolean sample) throws IOException {
        return getLines(day, sample).get(0);
    }
    
    public static String getString(int day) throws IOException {
        return getString(day, false);
    }

}
