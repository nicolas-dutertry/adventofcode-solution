package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Direction;
import com.dutertry.adventofcode.Point;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day16_1 {
    private static record State(Point position, Direction direction) {}
    private static record FullState(State state, long score) {}

    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(16));

            State startState = new State(map.find('S'), Direction.RIGHT);
            Map<State, Long> minScoresMap = new HashMap<>();
            Set<FullState> fullStates = Collections.singleton(new FullState(startState, 0L));
            long minEndScore = Long.MAX_VALUE;
            while(!fullStates.isEmpty()) {
                Set<FullState> newFullStates = new HashSet<>();
                for (FullState fullState : fullStates) {
                    State state = fullState.state();
                    long score = fullState.score();

                    if(score > minEndScore) {
                        continue;
                    }

                    if(map.getChar(state.position()) == 'E') {
                        minEndScore = score;
                    } else if(!minScoresMap.containsKey(state) || minScoresMap.get(state) > score) {
                        minScoresMap.put(state, score);
                        newFullStates.addAll(getNextPossibleStates(map, state, score));
                    }
                }
                fullStates = newFullStates;
            }

            System.out.println(minEndScore);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<FullState> getNextPossibleStates(AdventMap map, State state, long score) {
        Set<FullState> fullStates = new HashSet<>();
        Point nextPosition = state.direction.move(state.position);
        if(map.getChar(nextPosition) != '#') {
            long newScore = score+1;
            State newState = new State(nextPosition, state.direction);
            fullStates.add(new FullState(newState, newScore));
        }

        long newScore2 = score+1000;
        State newState2 = new State(state.position, state.direction.turnLeft());
        fullStates.add(new FullState(newState2, newScore2));

        long newScore = score+1000;
        State newState = new State(state.position, state.direction.turnRight());
        fullStates.add(new FullState(newState, newScore));

        return fullStates;
    }
}
