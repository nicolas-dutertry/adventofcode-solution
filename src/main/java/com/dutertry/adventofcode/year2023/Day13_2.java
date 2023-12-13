package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day13_2 {
    public static void main(String[] args) {
        List<List<String>> patterns = new ArrayList<>();
        List<String> currentPattern = new ArrayList<String>();
        patterns.add(currentPattern);
        try (BufferedReader br = AdventUtils.getBufferedReader(13)) {
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    currentPattern = new ArrayList<String>();
                    patterns.add(currentPattern);
                } else {
                    currentPattern.add(line);
                }
            }
            

            int total = 0;
            for (List<String> pattern : patterns) {
                int score = 0;
                int vMirror = getMirror(pattern);
                if(vMirror != -1) {
                    score += 100*vMirror;
                }
                List<String> inverted = invert(pattern);
                int hMirror = getMirror(inverted);
                if(hMirror != -1) {
                    score += hMirror;
                }
                total += score;
            }
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int getMirror(List<String> pattern) {
        int mirror = -1;
        for(int m = 0; m <= pattern.size()-1; m++) {
            int i = 0;
            boolean ok = true;
            int smudges = 0;
            while(true) {
                if(m-1-i < 0) {
                    break;
                }
                if(m+i > pattern.size()-1) {
                    break;
                }
                
                String s1 = pattern.get(m-1-i);
                String s2 = pattern.get(m+i);
                if(!s1.equals(s2)) {
                    if(almostEquals(s1, s2)) {
                        smudges++;
                    } else {
                        ok = false;
                        break;
                    }
                    if(smudges > 1) {
                        ok = false;
                        break;
                    }
                }
                i++;
            }
            if(ok && smudges == 1) {
                mirror = m;
            }
        }
        return mirror;
    }
    
    private static List<String> invert(List<String> pattern) {
        List<String> inverted = new ArrayList<>();
        String first = pattern.get(0);
        for(int i = 0; i < first.length(); i++) {
            StringBuilder s = new StringBuilder();
            for(int j = 0; j < pattern.size(); j++) {
                s.append(pattern.get(j).charAt(i));
            }
            inverted.add(s.toString());
        }
        return inverted;
    }
    
    private static boolean almostEquals(String s1, String s2) {
        int errors = 0;
        for(int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                errors++;
            }
            if(errors > 1) {
                break;
            }
        }
        return errors == 1;
    }

}
