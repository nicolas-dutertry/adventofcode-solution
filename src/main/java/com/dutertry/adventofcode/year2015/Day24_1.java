package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day24_1 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(24);
            List<Integer> packages = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
            int totalWeight = packages.stream().mapToInt(i->i).sum();
            int compartmentWeight = totalWeight/3;
            
            List<List<Integer>> possibles = getPossibles(packages, compartmentWeight);
            int minSize = Integer.MAX_VALUE;
            List<List<Integer>> smallpossibles = new LinkedList<>();
            for (List<Integer> possible : possibles) {
                if(possible.size() < minSize) {
                    minSize = possible.size();
                    smallpossibles = new LinkedList<>();
                    smallpossibles.add(possible);
                } else if(possible.size() == minSize) {
                    smallpossibles.add(possible);
                }
            }
            long minqe = Long.MAX_VALUE;
            for (List<Integer> possible : smallpossibles) {
                long qe = possible.stream().mapToLong(i -> (long)i).reduce(1, (l1, l2) -> l1*l2);
                if(qe < minqe) {
                    minqe = qe;
                }
            }
            System.out.println(minqe);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<List<Integer>> getPossibles(List<Integer> packages, int total) {
        List<List<Integer>> possibles = new LinkedList<>();
        if(packages.isEmpty()) {
            return possibles;
        }
        int p = packages.get(0);
        if(p == total) {
            List<Integer> possible = new LinkedList<>();
            possible.add(p);
            possibles.add(possible);
        }
        if(p < total) {
            List<List<Integer>> subpossibles = getPossibles(packages.subList(1, packages.size()), total-p);
            for (List<Integer> subpossible : subpossibles) {
                subpossible.add(0, p);
            }
            possibles.addAll(subpossibles);
        }
        possibles.addAll(getPossibles(packages.subList(1, packages.size()), total));
        return possibles;
    }
}
