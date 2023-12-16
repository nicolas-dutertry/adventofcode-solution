package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17_1 {
    
    public static void main(String[] args) {
        try {
            List<Integer> containers = AdventUtils.getLines(17).stream().map(Integer::parseInt).collect(Collectors.toList());
            List<List<Integer>> combinations = getCombinations(containers, 150);
            System.out.println(combinations.size());
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    private static List<List<Integer>> getCombinations(List<Integer> containers, int liters) {
        List<List<Integer>> combinations = new ArrayList<>();
        for (int i = 0; i < containers.size(); i++) {
            int container = containers.get(i);
            if(container == liters) {
                List<Integer> combination = new ArrayList<>();
                combination.add(container);
                combinations.add(combination);
            } else if(container < liters) {
                List<List<Integer>> subCombinations = getCombinations(containers.subList(i+1, containers.size()), liters-container);
                for (List<Integer> subCombination : subCombinations) {
                    subCombination.add(0, container);
                    combinations.add(subCombination);
                }
            }
        }
        return combinations;
    }
}
