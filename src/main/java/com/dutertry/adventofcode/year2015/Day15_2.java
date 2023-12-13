package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day15_2 {
    private static class Ingredient {
        public final int capacity;
        public final int durability;
        public final int flavor;
        public final int texture;
        public final int calories;
        
        public Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }
    
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(15)) {
            String line;
            List<Ingredient> ingredients = new LinkedList<>();
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line);
                int capacity = Integer.parseInt(array[2].substring(0, array[2].length()-1));
                int durability = Integer.parseInt(array[4].substring(0, array[4].length()-1));
                int flavor = Integer.parseInt(array[6].substring(0, array[6].length()-1));
                int texture = Integer.parseInt(array[8].substring(0, array[8].length()-1));
                int calories = Integer.parseInt(array[10]);
                ingredients.add(new Ingredient(capacity, durability, flavor, texture, calories));
            }
            
            List<List<Integer>> spoonDistribution = getSpoonDistributions(ingredients.size(), 100);
            long maxScore = 0;
            for (List<Integer> distribution : spoonDistribution) {
                long capacity = 0;
                long durability = 0;
                long flavor = 0;
                long texture = 0;
                long calories = 0;
                for(int i = 0; i < distribution.size(); i++) {
                    int cups = distribution.get(i);
                    Ingredient ingredient = ingredients.get(i);
                    capacity += cups * ingredient.capacity;
                    durability += cups * ingredient.durability;
                    flavor += cups * ingredient.flavor;
                    texture += cups * ingredient.texture;
                    calories += cups * ingredient.calories;
                }
                if(calories == 500) {
                    if(capacity < 0) {
                        capacity = 0;
                    }
                    if(durability < 0) {
                        durability = 0;
                    }
                    if(flavor < 0) {
                        flavor = 0;
                    }
                    if(texture < 0) {
                        texture = 0;
                    }
                    long score = capacity*durability*flavor*texture;
                    if(score > maxScore) {
                        maxScore = score;
                    }
                }
            }
            System.out.println(maxScore);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    private static List<List<Integer>> getSpoonDistributions(int elementCount, int total) {
        List<List<Integer>> spoonDistributions = new ArrayList<>();
        if(elementCount == 1) {
            List<Integer> distribution = new ArrayList<Integer>();
            distribution.add(total);
            spoonDistributions.add(distribution);
            return spoonDistributions;
        }
        
        for(int i = 0; i <= total; i++) {
            List<List<Integer>> sub = getSpoonDistributions(elementCount-1, total-i);
            for (List<Integer> distribution : sub) {
                distribution.add(0, i);
                spoonDistributions.add(distribution);
            }
        }
        return spoonDistributions;
    }
    
    
}
