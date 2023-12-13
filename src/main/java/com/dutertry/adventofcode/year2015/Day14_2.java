package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Day14_2 {
    private static class Reindeer {
        public final String name;
        private final int speed;
        private final int flightTime;
        private final int restTime;
        
        public Reindeer(String name, int speed, int flightTime, int restTime) {
            this.name = name;
            this.speed = speed;
            this.flightTime = flightTime;
            this.restTime = restTime;
        }
        
        public int getDistance(int time) {
            int totalTime = flightTime+restTime;
            int completeCount = time / totalTime;
            int distance = completeCount*flightTime*speed;
            
            int remainingTime = time % totalTime;
            
            if(flightTime >= remainingTime) {
                distance = distance + remainingTime*speed;
                return distance;
            } else {
                distance = distance + flightTime*speed;
                return distance;
            }
        }
    }
    
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(14)) {
            String line;
            List<Reindeer> reindeers = new LinkedList<>();
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line);
                String name = array[0];
                int speed = Integer.parseInt(array[3]);
                int flightTime = Integer.parseInt(array[6]);
                int restTime = Integer.parseInt(array[13]);
                reindeers.add(new Reindeer(name, speed, flightTime, restTime));
            }
            
            Map<String, Integer> scoreMap = new HashMap<>();
            for (Reindeer reindeer : reindeers) {
                scoreMap.put(reindeer.name, 0);
            }
            
            for(int i = 1; i <= 2503; i++) {
                int maxDistance = -1;
                Set<String> leaders = new HashSet<>();
                for (Reindeer reindeer : reindeers) {
                    int distance = reindeer.getDistance(i);
                    if (distance > maxDistance) {
                        leaders = new HashSet<>();
                        leaders.add(reindeer.name);
                        maxDistance = distance;
                    } else if(distance == maxDistance) {
                        leaders.add(reindeer.name);
                    }
                }
                
                for (String leader : leaders) {
                    int score = scoreMap.get(leader);
                    score++;
                    scoreMap.put(leader, score);
                }
            }
            
            int maxScore = scoreMap.values().stream().mapToInt(i->i).max().orElse(0);
            System.out.println(maxScore);
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    
}
