package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class Day3_2 {
    private static class NumberLocation {
        public int line;
        public int pos;
        public int value;
        public List<StarPos> starPosList = new LinkedList<>();
        
        public NumberLocation(int line, int pos, int value) {
            this.line = line;
            this.pos = pos;
            this.value = value;
        }
        
        public boolean touchStar(List <StarPos> stars) {
            for (StarPos star : stars) {
                for (StarPos starPos : starPosList) {
                    if(starPos.equals(star)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    private static class StarPos {
        public int line;
        public int pos;
        
        public StarPos(int line, int pos) {
            this.line = line;
            this.pos = pos;
        }

        @Override
        public String toString() {
            return "[line=" + line + ", pos=" + pos + "]";
        }

        @Override
        public int hashCode() {
            return Objects.hash(line, pos);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            StarPos other = (StarPos) obj;
            return line == other.line && pos == other.pos;
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
            
            for (NumberLocation numberLocation : numberLocations) {
                checkStars(numberLocation, lines);
            }
            
            int sum = 0;
            for(int i = 0; i < numberLocations.size(); i++) {
                NumberLocation numberLocation = numberLocations.get(i);
                for(int j = i+1; j < numberLocations.size(); j++) {
                    NumberLocation numberLocation2 = numberLocations.get(j);
                    
                    if(numberLocation.touchStar(numberLocation2.starPosList)) {
                        sum += numberLocation.value * numberLocation2.value;
                    }
                    
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
    
    private static void checkStars(NumberLocation numberLocation, List<String> lines) {
        if(numberLocation.line > 0) {
            String line = lines.get(numberLocation.line-1);
            for(int i = numberLocation.pos-1; i <= numberLocation.pos+String.valueOf(numberLocation.value).length(); i++) {
                if(i >=0 && i < line.length()) {
                    char c = line.charAt(i);
                    if(isStar(c)) {
                        numberLocation.starPosList.add(new StarPos(numberLocation.line-1, i));
                    }
                }
            }
        }
        if(numberLocation.line < lines.size() - 1) {
            String line = lines.get(numberLocation.line+1);
            for(int i = numberLocation.pos-1; i <= numberLocation.pos+String.valueOf(numberLocation.value).length(); i++) {
                if(i >=0 && i < line.length()) {
                    char c = line.charAt(i);
                    if(isStar(c)) {
                        numberLocation.starPosList.add(new StarPos(numberLocation.line+1, i));
                    }
                }
            }
        }
        String line = lines.get(numberLocation.line);
        if(numberLocation.pos > 0) {
            char c = line.charAt(numberLocation.pos-1);
            if(isStar(c)) {
                numberLocation.starPosList.add(new StarPos(numberLocation.line, numberLocation.pos-1));
            }
        }
        if(numberLocation.pos+String.valueOf(numberLocation.value).length() < line.length()) {
            char c = line.charAt(numberLocation.pos+String.valueOf(numberLocation.value).length());
            if(isStar(c)) {
                numberLocation.starPosList.add(new StarPos(numberLocation.line, numberLocation.pos+String.valueOf(numberLocation.value).length()));
            }
        }
    }
    
    private static boolean isStar(char c) {
        return c ==  '*';
    }

}
