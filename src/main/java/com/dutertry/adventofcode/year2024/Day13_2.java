package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.LinearSystem;
import com.dutertry.adventofcode.Rational;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13_2 {
    private static record Machine(long aX, long aY, long bX, long bY, long prizeX, long prizeY) {}

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
                long prizeX = Integer.parseInt(matcherP.group(1)) + 10000000000000L;
                long prizeY = Integer.parseInt(matcherP.group(2)) + 10000000000000L;

                Machine machine = new Machine(aX, aY, bX, bY, prizeX, prizeY);
                machines.add(machine);
                line = br.readLine();
            }

            for (Machine machine : machines) {
                long[][] coefficients = {
                        {machine.aX, machine.bX},
                        {machine.aY, machine.bY}
                };
                long[] constants = {machine.prizeX, machine.prizeY};
                Rational[] solution = LinearSystem.solveSystem(coefficients, constants);
                if (solution[0].isInteger() && solution[1].isInteger()) {
                    long aCount = solution[0].numerator().longValueExact();
                    long bCount = solution[1].numerator().longValueExact();
                    total += 3L * aCount + bCount;
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
