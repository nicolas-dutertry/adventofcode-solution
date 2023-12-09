package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;

public class Day9_1 {
    private static Pattern PATTERN = Pattern.compile("([a-zA-Z]+) to ([a-zA-Z]+) = ([0-9]+)");
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(9)) {
            String line;
            Set<String> cities = new HashSet<>();
            Map<String, Integer> distanceMap = new HashMap<>();
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line.trim());
                if(!matcher.matches()) {
                    throw new RuntimeException("Oups");
                }
                
                String city1 = matcher.group(1);
                String city2 = matcher.group(2);
                cities.add(city1);
                cities.add(city2);
                int distance = Integer.parseInt(matcher.group(3));
                distanceMap.put(city1 + "->" + city2, distance);
                distanceMap.put(city2 + "->" + city1, distance);
            }
            
            Collection<List<String>> routes = CollectionUtils.permutations(cities);
            int minDistance = Integer.MAX_VALUE;
            for (List<String> route : routes) {
                int distance = 0;
                for(int i = 1; i < route.size(); i++) {
                    String city1 = route.get(i-1);
                    String city2 = route.get(i);
                    distance += distanceMap.get(city1 + "->" + city2);
                }
                if(distance < minDistance) {
                    minDistance = distance;
                }
            }
            System.out.println(minDistance);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
