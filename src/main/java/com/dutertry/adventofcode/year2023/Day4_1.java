package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Day4_1 {

    public static void main(String[] args) {
        int sum = 0;
        try(BufferedReader br = AdventUtils.getBufferedReader(4)) {
            String line;
            while((line = br.readLine()) != null ) {
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
                
                int res = 0;
                for (Integer integer : winnings) {
                    if(res == 0) {
                        res = 1;
                    } else {
                        res = 2 * res;
                    }
                }
                sum += res;
                
            }       
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }
}
