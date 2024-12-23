package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23_2 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(23)) {
            Set<Set<String>> lans = new HashSet<>();
            Map<String, Set<String>> computerLinks = new HashMap<>();

            String line;
            while((line = br.readLine()) != null ) {
                String[] array = StringUtils.split(line, "-");
                String c1 = array[0];
                String c2 = array[1];
                lans.add(Set.of(c1, c2));
                computerLinks.computeIfAbsent(c1, k -> new HashSet<>()).add(c2);
                computerLinks.computeIfAbsent(c2, k -> new HashSet<>()).add(c1);
            }

            while(true) {
                System.out.println(lans.size());
                Set<Set<String>> newlans = new HashSet<>();
                for (Set<String> lan : lans) {
                    String c1 = lan.iterator().next();
                    Set<String> computers = computerLinks.get(c1);
                    for (String computer : computers) {
                        if(lan.contains(computer)) {
                            continue;
                        }
                        boolean ok = true;
                        for (String c : lan) {
                            if(!computerLinks.get(c).contains(computer)) {
                                ok = false;
                                break;
                            }
                        }
                        if(ok) {
                            Set<String> newlan = new HashSet<>(lan);
                            newlan.add(computer);
                            newlans.add(newlan);
                        }
                    }
                }
                if(newlans.isEmpty()) {
                    break;
                }
                lans = newlans;
            }


            for (Set<String> lan : lans) {
                System.out.println(lan.stream().sorted().collect(Collectors.joining(",")));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
