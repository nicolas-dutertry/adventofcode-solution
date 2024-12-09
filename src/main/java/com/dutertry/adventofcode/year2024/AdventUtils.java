package com.dutertry.adventofcode.year2024;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AdventUtils {
    private static String getResourceName(int day, boolean sample) {
        String name = "input" + day;
        if(sample) {
            name += "-e";
        }
        name += ".txt";
        return name;
    }

    private static Reader getReader(int day, boolean sample) {
        return new InputStreamReader(AdventUtils.class.getResourceAsStream(getResourceName(day, sample)),
                StandardCharsets.UTF_8);
    }

    public static BufferedReader getBufferedReader(int day) {
        return getBufferedReader(day, false);
    }
    
    public static BufferedReader getBufferedReader(int day, boolean sample) {
        String name = getResourceName(day, sample);
        return new BufferedReader(getReader(day, sample));
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
        String s = IOUtils.toString(getReader(day, sample));
        if(s.endsWith("\n")) {
            s = s.substring(0, s.length() - 1);
        }
        if(s.endsWith("\r")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
    public static String getString(int day) throws IOException {
        return getString(day, false);
    }

}
