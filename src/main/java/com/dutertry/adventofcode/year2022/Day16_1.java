package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16_1 {
    private static final Pattern PATTERN = Pattern.compile("Valve ([A-Z][A-Z]) has flow rate=([0-9]+); tunnel(s)? lead(s)? to valve(s)? ([A-Z][A-Z, ]+)");

    private static record Valve(String name, int rate, Set<String> connectedValves) {}

    private static record Path(String currentValve, Set<String> fromValves, int score, Set<String> openedValves) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(16)) {
            Map<String, Valve> valves = new HashMap<>();
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    String valveName = matcher.group(1);
                    int rate = Integer.parseInt(matcher.group(2));
                    String[] connectedValves = matcher.group(6).split(", ");

                    valves.put(valveName, new Valve(valveName, rate, new HashSet<>(Arrays.asList(connectedValves))));
                } else {
                    throw new RuntimeException("Invalid input: " + line);
                }
            }
            Set<Valve> valvesByRate = new TreeSet<>((v1, v2) -> Integer.compare(v2.rate(), v1.rate()));
            valvesByRate.addAll(valves.values());
            System.out.println(valvesByRate);

            List<Path> paths = new LinkedList<>();
            paths.add(new Path("AA", Collections.emptySet(),0, new HashSet<>()));
            int maxScore = 0;
            for(int i = 30; i > 0; i--) {
                List<Path> nextPaths = new LinkedList<>();

                for (Path path : paths) {
                    if(getMaxExpectedScore(path, valves, valvesByRate, i) < maxScore) {
                        continue;
                    }
                    if(!path.openedValves.contains(path.currentValve)) {
                        int valveRate = valves.get(path.currentValve()).rate();
                        if(valveRate != 0) {
                            Set<String> openedValves = new HashSet<>(path.openedValves());
                            openedValves.add(path.currentValve());
                            int score = path.score() + valves.get(path.currentValve()).rate() * (i - 1);
                            nextPaths.add(new Path(path.currentValve(), Collections.emptySet(), score, openedValves));
                            if(score > maxScore) {
                                maxScore = score;
                            }
                        }
                    }
                    Valve valve = valves.get(path.currentValve);
                    for (String connectedValve : valve.connectedValves()) {
                        if(path.fromValves().contains(connectedValve)) {
                            continue;
                        }
                        Set<String> fromValves = new HashSet<>(path.fromValves());
                        fromValves.add(path.currentValve());
                        nextPaths.add(new Path(connectedValve, fromValves, path.score(), path.openedValves()));
                    }
                }
                paths = nextPaths;
            }

            System.out.println(maxScore);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMaxExpectedScore(Path path, Map<String, Valve> valves, Set<Valve> valvesByRate, int time) {
        int score = path.score();

        int i = time - 1;
        for (Valve valve : valvesByRate) {
            if(!path.openedValves.contains(valve.name())) {
                int valveRate = valve.rate();
                score += valveRate * i;
                i = i - 2;
                if(i <= 0) {
                    break;
                }
            }
        }
        return score;
    }
}
