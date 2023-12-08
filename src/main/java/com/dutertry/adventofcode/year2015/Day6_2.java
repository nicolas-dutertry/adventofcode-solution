package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6_2 {
    private static Pattern PATTERN = Pattern.compile("([a-z ]+) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)");

	public static void main(String[] args) {
	    long[][] lights = new long[1000][1000];
	    
		try(BufferedReader br = AdventUtils.getBufferedReader(6)) {
        	String line;
        	while((line = br.readLine()) != null) {
        	    Matcher matcher = PATTERN.matcher(line);
        		if(!matcher.matches()) {
        		    throw new RuntimeException("Oups");
        		}
        		
        		String action = matcher.group(1);
        		int topx = Integer.parseInt(matcher.group(2));
        		int topy = Integer.parseInt(matcher.group(3));
        		int bottomx = Integer.parseInt(matcher.group(4));
                int bottomy = Integer.parseInt(matcher.group(5));
                
                for(int x = topx; x <= bottomx; x++) {
                    for(int y = topy; y <= bottomy; y++) {
                        switch(action) {
                            case "turn on":
                                lights[x][y] = lights[x][y] + 1;
                                break;
                            case "turn off":
                                if(lights[x][y] > 0) {
                                    lights[x][y] = lights[x][y] - 1;
                                }
                                break;
                            case "toggle":
                                lights[x][y] = lights[x][y] + 2;
                                break;
                            default:
                                throw new RuntimeException("Oups");
                        }
                    }
                }
        	}
        	int total = 0;
            for(int x = 0; x < 1000; x++) {
                for(int y = 0; y < 1000; y++) {
                    total += lights[x][y];
                }
            }
            System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
