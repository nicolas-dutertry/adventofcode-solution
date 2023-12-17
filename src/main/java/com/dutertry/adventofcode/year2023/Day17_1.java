package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day17_1 {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    
    private static class SimpleState {
        public final int x;
        public final int y;
        public final String direction;
        public final int straightCount;
        
        public SimpleState(int x, int y, String direction, int straightCount) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.straightCount = straightCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, straightCount, x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SimpleState other = (SimpleState) obj;
            return Objects.equals(direction, other.direction) && straightCount == other.straightCount && x == other.x
                    && y == other.y;
        }
    }
    
    private static class State {
        public final int x;
        public final int y;
        public final String direction;
        public final int straightCount;
        public final int heat;
        
        public State(int x, int y, String direction, int straightCount, int heat) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.straightCount = straightCount;
            this.heat = heat;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(17);
            List<List<Integer>> map = new ArrayList<>();
            for (String line : lines) {
                List<Integer> heatLine = new ArrayList<>();
                for(int i = 0; i < line.length(); i++) {
                    heatLine.add(Integer.parseInt(String.valueOf(line.charAt(i))));
                }
                map.add(heatLine);
            }
            int maxx = map.get(0).size()-1;
            int maxy = map.size()-1;
            
            int minHeat = Integer.MAX_VALUE;
            Map<SimpleState, Integer> simpleStateMap = new HashMap<>();
            simpleStateMap.put(new SimpleState(1, 0, RIGHT, 1), 1);
            simpleStateMap.put(new SimpleState(0, 1, DOWN, 1), 1);
            List<State> states = new LinkedList<>();
            states.add(new State(1, 0, RIGHT, 1, getHeat(map, 1, 0)));
            states.add(new State(0, 1, DOWN, 1, getHeat(map, 0, 1)));
            
            while(!states.isEmpty()) {
                List<State> nextStates = new LinkedList<>();
                for (State state : states) {
                    if(state.x == maxx && state.y == maxy) {
                        if(state.heat < minHeat) {
                            minHeat = state.heat;
                        }                        
                    } else {
                        nextStates.addAll(getNextPossibleStates(map, state, simpleStateMap, minHeat));
                    }
                }
                
                states = nextStates;
            }
            System.out.println(minHeat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<State> getNextPossibleStates(List<List<Integer>> map, State state, Map<SimpleState, Integer> simpleStateMap, int minHeat) {
        int maxx = map.get(0).size()-1;
        int maxy = map.size()-1;
        List<State> states = new LinkedList<>();
        if(state.straightCount < 3) {
            int nextx = state.x;
            int nexty = state.y;
            switch(state.direction) {
            case LEFT:
                nextx--;
                break;
            case RIGHT:
                nextx++;
                break;
            case UP:
                nexty--;
                break;
            case DOWN:
                nexty++;
                break;
            default:
                throw new RuntimeException("Oups");
            }
            if(nextx <= maxx && nextx >= 0 && nexty <= maxy && nexty >= 0) {
                int nextHeat = state.heat + getHeat(map, nextx, nexty);
                if(nextHeat < minHeat) {
                    SimpleState simpleState = new SimpleState(nextx, nexty, state.direction, state.straightCount+1);
                    Integer maxHeat = simpleStateMap.get(simpleState);
                    if(maxHeat == null || nextHeat < maxHeat) {
                        simpleStateMap.put(simpleState, nextHeat);
                        states.add(new State(nextx, nexty, state.direction, state.straightCount+1, nextHeat));
                    }
                }
            }
        }
        
        int nextx = state.x;
        int nexty = state.y;
        String nextDirection = null;
        switch(state.direction) {
        case LEFT:
            nexty--;
            nextDirection = UP;
            break;
        case RIGHT:
            nexty++;
            nextDirection = DOWN;
            break;
        case UP:
            nextx++;
            nextDirection = RIGHT;
            break;
        case DOWN:
            nextx--;
            nextDirection = LEFT;
            break;
        default:
            throw new RuntimeException("Oups");
        }
        if(nextx <= maxx && nextx >= 0 && nexty <= maxy && nexty >= 0) {
            int nextHeat = state.heat + getHeat(map, nextx, nexty);
            if(nextHeat < minHeat) {
                SimpleState simpleState = new SimpleState(nextx, nexty, nextDirection, 1);
                Integer maxHeat = simpleStateMap.get(simpleState);
                if(maxHeat == null || nextHeat < maxHeat) {
                    simpleStateMap.put(simpleState, nextHeat);
                    states.add(new State(nextx, nexty, nextDirection, 1, nextHeat));
                }
            }
        }
        
        nextx = state.x;
        nexty = state.y;
        nextDirection = null;
        switch(state.direction) {
        case LEFT:
            nexty++;
            nextDirection = DOWN;
            break;
        case RIGHT:
            nexty--;
            nextDirection = UP;
            break;
        case UP:
            nextx--;
            nextDirection = LEFT;
            break;
        case DOWN:
            nextx++;
            nextDirection = RIGHT;
            break;
        default:
            throw new RuntimeException("Oups");
        }
        if(nextx <= maxx && nextx >= 0 && nexty <= maxy && nexty >= 0) {
            int nextHeat = state.heat + getHeat(map, nextx, nexty);
            if(nextHeat < minHeat) {
                SimpleState simpleState = new SimpleState(nextx, nexty, nextDirection, 1);
                Integer maxHeat = simpleStateMap.get(simpleState);
                if(maxHeat == null || nextHeat < maxHeat) {
                    simpleStateMap.put(simpleState, nextHeat);
                    states.add(new State(nextx, nexty, nextDirection, 1, nextHeat));
                }
            }
        }
        return states;
    }
    
    private static int getHeat(List<List<Integer>> map, int x, int y) {
        return map.get(y).get(x);
    }
}
