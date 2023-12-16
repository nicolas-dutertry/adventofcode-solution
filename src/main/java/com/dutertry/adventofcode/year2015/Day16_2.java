package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day16_2 {
    
    public static void main(String[] args) {
        Map<String, Integer> giftMap = Map.of(
            "children", 3,
            "cats", 7,
            "samoyeds", 2,
            "pomeranians", 3,
            "akitas", 0,
            "vizslas", 0,
            "goldfish", 5,
            "trees", 3,
            "cars", 2,
            "perfumes", 1);
        try(BufferedReader br = AdventUtils.getBufferedReader(16)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line, ":,");
                String aunt = array[0];
                boolean ok = true;
                for(int i = 1; i < array.length; i=i+2) {
                    String compound = array[i].trim();
                    int count = Integer.parseInt(array[i+1].trim());
                    Integer giftCount = giftMap.get(compound);
                    if(compound.equals("cats") || compound.equals("trees")) {
                        if(count <= giftCount) {
                            ok = false;
                            break;
                        }
                    } else if(compound.equals("pomeranians") || compound.equals("goldfish")) {
                        if(count >= giftCount) {
                            ok = false;
                            break;
                        }
                    } else {
                        if(count != giftCount) {
                            ok = false;
                            break;
                        }
                    }
                }
                if(ok) {
                    System.out.println(aunt);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
