package com.dutertry.adventofcode.year2022;

import org.apache.commons.collections4.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19_1 {
    private static final Pattern PATTERN = Pattern.compile(
            "Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore. Each clay robot costs ([0-9]+) ore. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian.");

    private static record Blueprint(int id, int oreRobotOreCost, int clayRobotOreCost, int obsidianRobotOreCost, int obsidianRobotClayCost, int geodeRobotOreCost, int geodeRobotObsidianCost) {
        public int getMaxSpentOre() {
            return Math.max(oreRobotOreCost, Math.max(clayRobotOreCost, Math.max(obsidianRobotOreCost, geodeRobotOreCost)));
        }

        public int getMaxSpentClay() {
            return obsidianRobotClayCost;
        }

        public int getMaxSpentObsidian() {
            return geodeRobotObsidianCost;
        }
    }

    private static record State(int oreCount, int clay, int obsidianCount, int geodeCount, int oreRobotCount, int clayRobotCount, int obsidianRobotCount, int geodeRobotCount) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(19)) {
            List<Blueprint> blueprints = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    int id = Integer.parseInt(matcher.group(1));
                    int oreRobotOreCost = Integer.parseInt(matcher.group(2));
                    int clayRobotOreCost = Integer.parseInt(matcher.group(3));
                    int obsidianRobotOreCost = Integer.parseInt(matcher.group(4));
                    int obsidianRobotClayCost = Integer.parseInt(matcher.group(5));
                    int geodeRobotOreCost = Integer.parseInt(matcher.group(6));
                    int geodeRobotObsidianCost = Integer.parseInt(matcher.group(7));

                    blueprints.add(new Blueprint(id, oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost));
                } else {
                    throw new RuntimeException("Invalid input: " + line);
                }
            }

            int total = 0;
            for (Blueprint blueprint : blueprints) {
                State initialState = new State(0, 0, 0, 0, 1, 0, 0, 0);
                Set<State> states = new HashSet<>();
                states.add(initialState);

                int maxGeodes = 0;
                int MAX = 24;
                for(int i = 0; i < MAX; i++) {
                    Set<State> nextStates = new HashSet<>();

                    for(State state : states) {
                        Set<State> nextRobotStates = (i == MAX-1 ? Collections.singleton(state) : buildNextRobotStates(blueprint, state));
                        for(State nextRobotState : nextRobotStates) {
                            State nextState = new State(nextRobotState.oreCount + state.oreRobotCount, nextRobotState.clay + state.clayRobotCount, nextRobotState.obsidianCount + state.obsidianRobotCount, nextRobotState.geodeCount + state.geodeRobotCount, nextRobotState.oreRobotCount, nextRobotState.clayRobotCount, nextRobotState.obsidianRobotCount, nextRobotState.geodeRobotCount);
                            if(nextState.geodeCount > maxGeodes) {
                                maxGeodes = nextState.geodeCount;
                            }
                            nextStates.add(nextState);
                        }
                    }
                    states = nextStates;
                }

                total += maxGeodes*blueprint.id();
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<State> buildNextRobotStates(Blueprint blueprint, State state) {
        Set<State> nextStates = new HashSet<>();

        CollectionUtils.addIgnoreNull(nextStates, buildGeodeRobot(blueprint, state));
        CollectionUtils.addIgnoreNull(nextStates, buildObsidianRobot(blueprint, state));
        CollectionUtils.addIgnoreNull(nextStates, buildClayRobot(blueprint, state));
        CollectionUtils.addIgnoreNull(nextStates, buildOreRobot(blueprint, state));
        nextStates.add(state);

        return nextStates;
    }

    private static State buildOreRobot(Blueprint blueprint, State state) {
        if(state.oreRobotCount >= blueprint.getMaxSpentOre()) {
            return null;
        }
        if(blueprint.oreRobotOreCost > state.oreCount) {
            return null;
        } else  {
            return new State(state.oreCount - blueprint.oreRobotOreCost, state.clay, state.obsidianCount, state.geodeCount, state.oreRobotCount + 1, state.clayRobotCount, state.obsidianRobotCount, state.geodeRobotCount);
        }
    }

    private static State buildClayRobot(Blueprint blueprint, State state) {
        if(state.clayRobotCount >= blueprint.getMaxSpentClay()) {
            return null;
        }
        if(blueprint.clayRobotOreCost > state.oreCount) {
            return null;
        } else  {
            return new State(state.oreCount - blueprint.clayRobotOreCost, state.clay, state.obsidianCount, state.geodeCount, state.oreRobotCount, state.clayRobotCount + 1, state.obsidianRobotCount, state.geodeRobotCount);
        }
    }

    private static State buildObsidianRobot(Blueprint blueprint, State state) {
        if(state.obsidianRobotCount >= blueprint.getMaxSpentObsidian()) {
            return null;
        }
        if(blueprint.obsidianRobotOreCost > state.oreCount || blueprint.obsidianRobotClayCost > state.clay) {
            return null;
        } else  {
            return new State(state.oreCount - blueprint.obsidianRobotOreCost, state.clay - blueprint.obsidianRobotClayCost, state.obsidianCount, state.geodeCount, state.oreRobotCount, state.clayRobotCount, state.obsidianRobotCount + 1, state.geodeRobotCount);
        }
    }

    private static State buildGeodeRobot(Blueprint blueprint, State state) {
        if(blueprint.geodeRobotOreCost > state.oreCount || blueprint.geodeRobotObsidianCost > state.obsidianCount) {
            return null;
        } else  {
            return new State(state.oreCount - blueprint.geodeRobotOreCost, state.clay, state.obsidianCount - blueprint.geodeRobotObsidianCost, state.geodeCount, state.oreRobotCount, state.clayRobotCount, state.obsidianRobotCount, state.geodeRobotCount + 1);
        }
    }
}
