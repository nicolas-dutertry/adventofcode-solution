package com.dutertry.adventofcode.year2025;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Model;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day10_2 {
    private record Button(int index, List<Integer> joltageIndexes) {}

    private record Machine(List<Integer> finalState, List<Button> buttons) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(10);
            List<Machine> machines = new ArrayList<>();
            for(String line : lines) {
                String[] parts = StringUtils.split(line, " ");

                String s = parts[parts.length - 1];
                s = s.substring(1, s.length() - 1);

                List<Integer> finalState = new ArrayList<>();
                for(String levelStr : StringUtils.split(s, ",")) {
                    finalState.add(Integer.parseInt(levelStr));
                }

                List<Button> buttons = new ArrayList<>();
                for(int i = 1; i < parts.length-1; i++) {
                    String buttonPart = parts[i].substring(1, parts[i].length() - 1);
                    List<Integer> joltageIndexes = new ArrayList<>();
                    for(String indexStr : StringUtils.split(buttonPart, ",")) {
                        joltageIndexes.add(Integer.parseInt(indexStr));
                    }
                    Button button = new Button(i-1, joltageIndexes);
                    buttons.add(button);
                }

                machines.add(new Machine(finalState, buttons));
            }


            long total = 0;

            for(int i = 0; i < machines.size(); i++) {
                Machine machine = machines.get(i);
                long best = findBestWithZ3(machine);
                total += best;
            }
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long findBestWithZ3(Machine machine) {
        Context ctx = new Context();
        Optimize opt = ctx.mkOptimize();

        // Une variable IntExpr par bouton (nombre de pressions)
        IntExpr[] buttonPresses = new IntExpr[machine.buttons.size()];
        for (int i = 0; i < machine.buttons.size(); i++) {
            buttonPresses[i] = ctx.mkIntConst("b" + i);
            // Contrainte : nombre de pressions >= 0
            opt.Add(ctx.mkGe(buttonPresses[i], ctx.mkInt(0)));
        }

        // Pour chaque joltage, la somme des pressions doit égaler l'état final
        for (int j = 0; j < machine.finalState.size(); j++) {
            List<ArithExpr> terms = new ArrayList<>();

            for (int i = 0; i < machine.buttons.size(); i++) {
                Button button = machine.buttons.get(i);
                if (button.joltageIndexes.contains(j)) {
                    terms.add(buttonPresses[i]);
                }
            }

            if (!terms.isEmpty()) {
                ArithExpr sum = ctx.mkAdd(terms.toArray(new ArithExpr[0]));
                opt.Add(ctx.mkEq(sum, ctx.mkInt(machine.finalState.get(j))));
            }
        }

        // Objectif : minimiser la somme totale des pressions
        ArithExpr totalPresses = ctx.mkAdd(buttonPresses);
        opt.MkMinimize(totalPresses);

        // Résolution
        if (opt.Check() == Status.SATISFIABLE) {
            Model model = opt.getModel();
            long result = ((IntNum) model.eval(totalPresses, false)).getInt64();
            ctx.close();
            return result;
        }

        ctx.close();
        return -1;
    }
}
