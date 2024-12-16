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

public class Day16_2 {
    private static record State(Point position, Direction direction) {}
    private static record FullState(State state, long score, State previousState) {}

    public static void main(String[] args) {
        try {
            AdventMap map = new AdventMap(AdventUtils.getLines(16));

            Point startPoint = map.find('S');
            State startState = new State(startPoint, Direction.RIGHT);

            Map<State, Long> minScoresMap = new HashMap<>();
            Map<State, Set<Point>> seatsMap = new HashMap<>();
            long minEndScore = Long.MAX_VALUE;
            Set<FullState> fullStates = Collections.singleton(new FullState(startState, 0L, null));
            while(!fullStates.isEmpty()) {
                Set<FullState> newFullStates = new HashSet<>();
                for (FullState fullState : fullStates) {
                    State state = fullState.state();
                    long score = fullState.score();

                    if(score > minEndScore) {
                        continue;
                    }

                    Long minStateScore = minScoresMap.get(state);
                    if(minStateScore == null || minStateScore >= score) {
                        Set<Point> newSeats = new HashSet<>();
                        if(minStateScore != null && minStateScore == score) {
                            newSeats.addAll(seatsMap.get(state));
                        }
                        State previousState = fullState.previousState();
                        if(previousState != null) {
                            newSeats.addAll(seatsMap.get(previousState));
                        }

                        newSeats.add(state.position());
                        seatsMap.put(state, newSeats);

                        minScoresMap.put(state, score);

                        if(map.getChar(state.position()) == 'E') {
                            minEndScore = score;
                        } else {
                            populateNextPossibleStates(map, state, score, newFullStates);
                        }
                    }
                }
                fullStates = newFullStates;
            }

            Point finalPoint = map.find('E');
            Set<Point> finalSeats = new HashSet<>();
            for(Direction direction : Direction.values()) {
                State finalState = new State(finalPoint, direction);
                Set<Point> seats = seatsMap.get(finalState);
                if(seats != null && minScoresMap.get(finalState) == minEndScore) {
                    finalSeats.addAll(seats);
                }
            }

            System.out.println(finalSeats.size());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void populateNextPossibleStates(AdventMap map, State state, long score, Set<FullState> fullStates) {
        Point nextPosition = state.direction.move(state.position);
        if(map.getChar(nextPosition) != '#') {
            long newScore = score+1;
            State newState = new State(nextPosition, state.direction);
            fullStates.add(new FullState(newState, newScore, state));
        }

        long newScore2 = score+1000;
        State newState2 = new State(state.position, state.direction.turnLeft());
        fullStates.add(new FullState(newState2, newScore2, state));

        long newScore3 = score+1000;
        State newState3 = new State(state.position, state.direction.turnRight());
        fullStates.add(new FullState(newState3, newScore3, state));
    }
}
