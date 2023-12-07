package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day2_1 {
    private static class Try {
        public int red = 0;
        public int green = 0;
        public int blue = 0;
        
        public int getRed() {
            return red;
        }
        public void setRed(int red) {
            this.red = red;
        }
        public int getGreen() {
            return green;
        }
        public void setGreen(int green) {
            this.green = green;
        }
        public int getBlue() {
            return blue;
        }
        public void setBlue(int blue) {
            this.blue = blue;
        }

    }

    private static Pattern gamePattern = Pattern.compile("Game ([0-9]+)");
    private static Pattern cubePattern = Pattern.compile("([0-9]+) ([a-z]+)");
    
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
            String line;
            int sum = 0;
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line, ":");
                int gameNumber = getGameNumber(array[0]);
                
                String[] tries = StringUtils.split(array[1].trim(), ";");
                List<Try> trs = Arrays.asList(tries).stream()
                        .map(Day2_1::parseTry)
                        .collect(Collectors.toList());
                
                boolean ok = true;
                for (Try tr : trs) {
                    if(!testTry(tr)) {
                        ok = false;
                        break;
                    }
                }
                
                if(ok) {
                    sum += gameNumber;
                }
            }
            
            System.out.println(sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int getGameNumber(String game) {
        Matcher matcher = gamePattern.matcher(game);
        if(matcher.matches()) {
            String s = matcher.group(1);
            return Integer.parseInt(s);
        } else {
            throw new RuntimeException("Game do not match");
        }
    }
    
    private static Try parseTry(String stry) {
        Try tr = new Try();
        String[] array = StringUtils.split(stry.trim(), ",");
        for (String s : array) {
            Matcher matcher = cubePattern.matcher(s.trim());
            matcher.matches();
            int count = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);
            switch (color) {
            case "red":
                tr.setRed(count);
                break;
            case "green":
                tr.setGreen(count);
                break;
            case "blue":
                tr.setBlue(count);
                break;
            default:
                throw new RuntimeException("oups");
            }
        }
        return tr;
    }
    
    private static boolean testTry(Try tr) {
        return tr.getRed() <= 12 && tr.getGreen() <= 13 && tr.getBlue() <= 14;
    }

}
