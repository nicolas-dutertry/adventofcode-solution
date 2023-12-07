package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day3_1 {
    private static class NumberLocation {
        public int line;
        public int pos;
        public int value;
        
        public NumberLocation(int line, int pos, int value) {
            super();
            this.line = line;
            this.pos = pos;
            this.value = value;
        }
        
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(3)) {
            List<String> lines = new LinkedList<>();
            List<NumberLocation> numberLocations = new LinkedList<>();
            String line;
            int lineIdx = 0;
            while((line = br.readLine()) != null ) {
                lines.add(line);
                numberLocations.addAll(parseLine(line, lineIdx));
                lineIdx++;
            }
            
            int sum = 0;
            for (NumberLocation numberLocation : numberLocations) {
                boolean part = isPartNumber(numberLocation, lines);
                if(part) {
                    sum += numberLocation.value;
                }
                
            }
            System.out.println(sum);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<NumberLocation> parseLine(String line, int lineIdx) {
        List<NumberLocation> numberLocations = new LinkedList<>();
        String currentNumber = "";
        for(int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if(c >= '0' && c <= '9') {
                currentNumber += c;
            } else {
                if(StringUtils.isNotEmpty(currentNumber)) {
                    numberLocations.add(new NumberLocation(lineIdx, i-currentNumber.length(), Integer.parseInt(currentNumber)));
                    currentNumber = "";
                }
            }
        }
        if(StringUtils.isNotEmpty(currentNumber)) {
            numberLocations.add(new NumberLocation(lineIdx, line.length()-currentNumber.length(), Integer.parseInt(currentNumber)));
        }
        return numberLocations;
    }
    
    private static boolean isPartNumber(NumberLocation numberLocation, List<String> lines) {
        if(numberLocation.line > 0) {
            String line = lines.get(numberLocation.line-1);
            for(int i = numberLocation.pos-1; i <= numberLocation.pos+String.valueOf(numberLocation.value).length(); i++) {
                if(i >=0 && i < line.length()) {
                    char c = line.charAt(i);
                    if(isSymbol(c)) {
                        return true;
                    }
                }
            }
        }
        if(numberLocation.line < lines.size() - 1) {
            String line = lines.get(numberLocation.line+1);
            for(int i = numberLocation.pos-1; i <= numberLocation.pos+String.valueOf(numberLocation.value).length(); i++) {
                if(i >=0 && i < line.length()) {
                    char c = line.charAt(i);
                    if(isSymbol(c)) {
                        return true;
                    }
                }
            }
        }
        String line = lines.get(numberLocation.line);
        if(numberLocation.pos > 0) {
            char c = line.charAt(numberLocation.pos-1);
            if(isSymbol(c)) {
                return true;
            }
        }
        if(numberLocation.pos+String.valueOf(numberLocation.value).length() < line.length()) {
            char c = line.charAt(numberLocation.pos+String.valueOf(numberLocation.value).length());
            if(isSymbol(c)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isSymbol(char c) {
        if(c != '.' && (c < '0' || c > '9')) {
            return true;
        }
        return false;
    }

}
