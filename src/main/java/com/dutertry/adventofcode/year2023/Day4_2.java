package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Day4_2 {

    public static void main(String[] args) {
        Map<Integer, Integer> mapCard = new HashMap<>();
        try(BufferedReader br = AdventUtils.getBufferedReader(4)) {
            String line;
            int cardNumber = 1;
            while((line = br.readLine()) != null ) {
                addWeight(mapCard, cardNumber, 1);
                int cardWeight = mapCard.get(cardNumber); 
                
                int i = line.indexOf(':');
                line = line.substring(i+1);
                String[] array = StringUtils.split(line, "|");
                List<Integer> winners = Arrays.asList(StringUtils.split(array[0]))
                        .stream()
                        .map(String::trim)
                        .map(s -> Integer.parseInt(s))
                        .collect(Collectors.toList());
                List<Integer> numbers = Arrays.asList(StringUtils.split(array[1]))
                        .stream()
                        .map(String::trim)
                        .map(s -> Integer.parseInt(s))
                        .collect(Collectors.toList());
                
                Collection<Integer> winnings = CollectionUtils.intersection(winners, numbers);
                
                int idx = 1;
                for (Integer integer : winnings) {
                    addWeight(mapCard, cardNumber+idx, cardWeight);
                    idx++;
                }
                
                cardNumber++;
            }
            
            int sum = 0;
            for (Integer val : mapCard.values()) {
                sum += val;
            }
            System.out.println(sum);
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private static void addWeight(Map<Integer, Integer> mapCard, int cardNumber, int weight) {
        Integer i = mapCard.get(cardNumber);
        if(i == null) {
            i = weight;
        } else {
            i = i + weight;
        }
        mapCard.put(cardNumber, i);
    }
}
