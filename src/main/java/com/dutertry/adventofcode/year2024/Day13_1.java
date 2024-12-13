package com.dutertry.adventofcode.year2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13_1 {
    private static record Machine(int aX, int aY, int bX, int bY, int prizeX, int prizeY) {}

    private static record Play(int aCount, int bCount, int x, int y) {}

    private static final Pattern PATTERN_BUTTON = Pattern.compile(
            "Button [AB]: X\\+([0-9]+), Y\\+([0-9]+)");
    private static final Pattern PATTERN_PRIZE = Pattern.compile(
            "Prize: X=([0-9]+), Y=([0-9]+)");

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(13)) {
            long total = 0;

            List<Machine> machines = new ArrayList<>();
            String line;

            while((line = br.readLine()) != null ) {
                Matcher matcherA = PATTERN_BUTTON.matcher(line);
                matcherA.matches();
                int aX = Integer.parseInt(matcherA.group(1));
                int aY = Integer.parseInt(matcherA.group(2));

                line = br.readLine();
                Matcher matcherB = PATTERN_BUTTON.matcher(line);
                matcherB.matches();
                int bX = Integer.parseInt(matcherB.group(1));
                int bY = Integer.parseInt(matcherB.group(2));

                line = br.readLine();
                Matcher matcherP = PATTERN_PRIZE.matcher(line);
                matcherP.matches();
                int prizeX = Integer.parseInt(matcherP.group(1));
                int prizeY = Integer.parseInt(matcherP.group(2));

                Machine machine = new Machine(aX, aY, bX, bY, prizeX, prizeY);
                machines.add(machine);
                line = br.readLine();
            }

            for (Machine machine : machines) {
                Set<Play> plays = getPlays(machine, new Play(0, 0, 0, 0), new HashSet<>());
                long cost = 0;
                if (!plays.isEmpty()) {
                    cost = Long.MAX_VALUE;
                    for (Play play : plays) {
                        cost = Math.min(cost, 3L * play.aCount + play.bCount);
                    }
                }
                total += cost;
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Play> getPlays(Machine machine, Play play, Set<Play> donePlays) {
        Set<Play> newPlays = new HashSet<>();

        int newX = play.x + machine.aX;
        int newY = play.y + machine.aY;

        Play newPlay = new Play(play.aCount + 1, play.bCount, newX, newY);
        if(!donePlays.contains(newPlay)) {
            donePlays.add(newPlay);
            if (newX == machine.prizeX && newY == machine.prizeY) {
                newPlays.add(newPlay);
            } else if (newX < machine.prizeX && newY < machine.prizeY) {
                newPlays.addAll(getPlays(machine, newPlay, donePlays));
            }
        }

        newX = play.x + machine.bX;
        newY = play.y + machine.bY;
        newPlay = new Play(play.aCount, play.bCount + 1, newX, newY);
        if(!donePlays.contains(newPlay)) {
            donePlays.add(newPlay);
            if (newX == machine.prizeX && newY == machine.prizeY) {
                newPlays.add(newPlay);
            } else if (newX < machine.prizeX && newY < machine.prizeY) {
                newPlays.addAll(getPlays(machine, newPlay, donePlays));
            }
        }

        return newPlays;
    }
}
