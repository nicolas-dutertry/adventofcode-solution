package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day14_1 {
    private static class Reindeer {
        private final int speed;
        private final int flightTime;
        private final int restTime;
        
        public Reindeer(int speed, int flightTime, int restTime) {
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
                int speed = Integer.parseInt(array[3]);
                int flightTime = Integer.parseInt(array[6]);
                int restTime = Integer.parseInt(array[13]);
                reindeers.add(new Reindeer(speed, flightTime, restTime));
            }
            
            int maxDistance = reindeers.stream().mapToInt(r -> r.getDistance(2503)).max().orElse(0);
            System.out.println(maxDistance);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    
}
