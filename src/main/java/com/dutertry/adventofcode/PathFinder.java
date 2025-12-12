package com.dutertry.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class PathFinder {
    public record WeightedPath<State>(List<State> path, long weight) {}
    public record CacheKey<State>(State start, State end) {}

    public static <State> List<WeightedPath<State>> findAllBestPaths(State start, State end, Function<State, Map<State, Long>> nextStates) {
        Predicate<State> isEnd = state -> Objects.equals(state, end);
        return findAllBestPaths(start, isEnd, nextStates);
    }

    public static <State> List<WeightedPath<State>> findAllBestPaths(State start, Predicate<State> isEnd, Function<State, Map<State, Long>> nextStates) {
        List<WeightedPath<State>> bestPaths = new LinkedList<>();

        List<WeightedPath<State>> possiblePaths = new LinkedList<>();
        possiblePaths.add(new WeightedPath<>(List.of(start), 0L));

        Map<State, Long> visitedStateMap = new HashMap<>();
        visitedStateMap.put(start, 0L);

        long bestWeight = Long.MAX_VALUE;

        while(!possiblePaths.isEmpty()) {
            List<WeightedPath<State>> nextPossiblePath = new LinkedList<>();
            for(WeightedPath<State> path : possiblePaths) {
                if(path.weight() > bestWeight) {
                    continue;
                }

                State lastState = path.path().get(path.path().size() - 1);
                Map<State, Long> nextStatesMap = nextStates.apply(lastState);
                for (Map.Entry<State, Long> entry : nextStatesMap.entrySet()) {
                    State nextState = entry.getKey();
                    long weight = entry.getValue();
                    long newWeight = path.weight() + weight;
                    long visitedWeight = visitedStateMap.getOrDefault(nextState, Long.MAX_VALUE);
                    if(newWeight <= bestWeight && newWeight <= visitedWeight) {
                        visitedStateMap.put(nextState, newWeight);
                        List<State> newPath = new ArrayList<>(path.path());
                        newPath.add(nextState);
                        WeightedPath<State> newWeightedPath = new WeightedPath<>(newPath, newWeight);

                        if(isEnd.test(nextState)) {
                            if(newWeight < bestWeight) {
                                bestPaths = new LinkedList<>();
                                bestWeight = newWeight;
                            }
                            bestPaths.add(newWeightedPath);
                        } else {
                            nextPossiblePath.add(newWeightedPath);
                        }
                    }
                }
            }
            possiblePaths = nextPossiblePath;
        }
        return bestPaths;
    }

    public static <State> WeightedPath<State> findAnyBestPath(State start, State end, Function<State, Map<State, Long>> nextStates) {
        Predicate<State> isEnd = state -> Objects.equals(state, end);
        return findAnyBestPath(start, isEnd, nextStates);
    }

    public static <State> WeightedPath<State> findAnyBestPath(State start, Predicate<State> isEnd, Function<State, Map<State, Long>> nextStates) {
        WeightedPath<State> bestPath = null;

        List<WeightedPath<State>> possiblePaths = new LinkedList<>();
        possiblePaths.add(new WeightedPath<>(List.of(start), 0L));

        Map<State, Long> visitedStateMap = new HashMap<>();
        visitedStateMap.put(start, 0L);

        long bestWeight = Long.MAX_VALUE;

        while(!possiblePaths.isEmpty()) {
            List<WeightedPath<State>> nextPossiblePath = new LinkedList<>();
            for(WeightedPath<State> path : possiblePaths) {
                if(path.weight() >= bestWeight) {
                    continue;
                }

                State lastState = path.path().get(path.path().size() - 1);
                Map<State, Long> nextStatesMap = nextStates.apply(lastState);
                for (Map.Entry<State, Long> entry : nextStatesMap.entrySet()) {
                    State nextState = entry.getKey();
                    long weight = entry.getValue();
                    long newWeight = path.weight() + weight;
                    long visitedWeight = visitedStateMap.getOrDefault(nextState, Long.MAX_VALUE);
                    if(newWeight < bestWeight && newWeight < visitedWeight) {
                        visitedStateMap.put(nextState, newWeight);
                        List<State> newPath = new ArrayList<>(path.path());
                        newPath.add(nextState);
                        WeightedPath<State> newWeightedPath = new WeightedPath<>(newPath, newWeight);

                        if(isEnd.test(nextState)) {
                            bestWeight = newWeight;
                            bestPath = newWeightedPath;
                        } else {
                            nextPossiblePath.add(newWeightedPath);
                        }
                    }
                }
            }
            possiblePaths = nextPossiblePath;
        }
        return bestPath;
    }

    public static <State> long countPaths(State start, State end, Function<State, List<State>> nextStatesProvider) {
        return countPaths(start, end, nextStatesProvider, new HashMap<>());
    }

    private static <State> long countPaths(State start, State end, Function<State, List<State>> nextStatesProvider,
                                   Map<CacheKey<State>, Long> cache) {
        CacheKey<State> cacheKey = new CacheKey<>(start, end);
        if(cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        if(start.equals(end)) {
            return 1L;
        }

        List<State> nextStates = nextStatesProvider.apply(start);
        if(nextStates.isEmpty()) {
            cache.put(cacheKey, 0L);
            return 0;
        }

        long result = 0;
        for(State nextState : nextStates) {
            long count = countPaths(nextState, end, nextStatesProvider, cache);
            result += count;
        }
        cache.put(cacheKey, result);
        return result;
    }

}
