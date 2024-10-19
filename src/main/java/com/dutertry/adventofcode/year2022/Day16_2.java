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

public class Day16_2 {
    private static final Pattern PATTERN = Pattern.compile("Valve ([A-Z][A-Z]) has flow rate=([0-9]+); tunnel(s)? lead(s)? to valve(s)? ([A-Z][A-Z, ]+)");

    private static record Valve(String name, int rate, Set<String> connectedValves) {}

    private static record Path(String currentValve, Set<String> fromValves, String currentElephantValve, Set<String> fromElephantValves, int score, Set<String> openedValves) {}

    private static record State(Set<String> positions, Set<String> openValves) {}

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

            List<Path> paths = new LinkedList<>();
            Path firstPath = new Path("AA", Collections.emptySet(), "AA", Collections.emptySet(),0, new HashSet<>());
            paths.add(firstPath);

            int maxScore = 0;
            Map<State, Integer> stateScores = new HashMap<>();
            for(int i = 26; i > 0; i--) {
                List<Path> nextPaths = new LinkedList<>();

                for (Path path : paths) {
                    if(getMaxExpectedScore(path, valves, valvesByRate, i) <= maxScore) {
                        continue;
                    }

                    State state = getState(path);
                    if(stateScores.containsKey(state)) {
                        if(stateScores.get(state) >= path.score()) {
                            continue;
                        }
                    }

                    stateScores.put(state, path.score());

                    if(!path.openedValves.contains(path.currentValve)) {
                        int valveRate = valves.get(path.currentValve()).rate();
                        if(valveRate != 0) {
                            Set<String> openedValves = new HashSet<>(path.openedValves());
                            openedValves.add(path.currentValve());
                            final int score = path.score() + valves.get(path.currentValve()).rate() * (i - 1);

                            if(!openedValves.contains(path.currentElephantValve)) {
                                int elephantValveRate = valves.get(path.currentElephantValve()).rate();
                                if(elephantValveRate != 0) {
                                    openedValves.add(path.currentElephantValve());
                                    int elscore = score + valves.get(path.currentElephantValve()).rate() * (i - 1);
                                    Path newPath = new Path(path.currentValve(), Collections.emptySet(), path.currentElephantValve(), Collections.emptySet(), elscore, openedValves);
                                    if(score > maxScore) {
                                        maxScore = score;
                                    }
                                    nextPaths.add(newPath);
                                }
                            }

                            Valve valve = valves.get(path.currentElephantValve());
                            for (String connectedElephantValve : valve.connectedValves()) {
                                if(path.fromElephantValves().contains(connectedElephantValve)) {
                                    continue;
                                }
                                Set<String> fromElephantValves = new HashSet<>(path.fromElephantValves());
                                fromElephantValves.add(path.currentElephantValve());
                                Path newPath = new Path(path.currentValve(), Collections.emptySet(), connectedElephantValve, fromElephantValves, score, openedValves);
                                if(score > maxScore) {
                                    maxScore = score;
                                }
                                nextPaths.add(newPath);
                            }
                        }
                    }
                    Valve valve = valves.get(path.currentValve());
                    for (String connectedValve : valve.connectedValves()) {
                        if(path.fromValves().contains(connectedValve)) {
                            continue;
                        }
                        Set<String> fromValves = new HashSet<>(path.fromValves());
                        fromValves.add(path.currentValve());

                        Set<String> openedValves = new HashSet<>(path.openedValves());
                        final int score = path.score();

                        if(!openedValves.contains(path.currentElephantValve())) {
                            int elephantValveRate = valves.get(path.currentElephantValve()).rate();
                            if(elephantValveRate != 0) {
                                openedValves.add(path.currentElephantValve());
                                int elscore = score + valves.get(path.currentElephantValve()).rate() * (i - 1);
                                Path newPath = new Path(connectedValve, fromValves, path.currentElephantValve(), Collections.emptySet(), elscore, openedValves);
                                if(score > maxScore) {
                                    maxScore = score;
                                }
                                nextPaths.add(newPath);
                            }
                        }

                        Valve elephantvalve = valves.get(path.currentElephantValve());
                        for (String connectedElephantValve : elephantvalve.connectedValves()) {
                            if(path.fromElephantValves().contains(connectedElephantValve)) {
                                continue;
                            }
                            Set<String> fromElephantValves = new HashSet<>(path.fromElephantValves());
                            fromElephantValves.add(path.currentElephantValve());
                            Path newPath = new Path(connectedValve, fromValves, connectedElephantValve, fromElephantValves, score, openedValves);
                            if(score > maxScore) {
                                maxScore = score;
                            }
                            nextPaths.add(newPath);
                        }
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
        int j = 0;
        for (Valve valve : valvesByRate) {
            if(!path.openedValves.contains(valve.name())) {
                int valveRate = valve.rate();
                score += valveRate * i;
                if(j % 2 == 1) {
                    i = i - 2;
                    if (i <= 0) {
                        break;
                    }
                }
                j++;
            }
        }
        return score;
    }

    private static State getState(Path path) {
        Set<String> positions;
        if(path.currentValve().equals(path.currentElephantValve())) {
            positions = Set.of(path.currentValve());
        } else {
            positions = Set.of(path.currentValve(), path.currentElephantValve());
        }
        return new State(positions, path.openedValves());
    }
}
