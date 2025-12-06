package com.dutertry.adventofcode.year2025;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day6_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(6)) {
            List<List<String>> problems = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null ) {
                String[] split = line.split(" ");
                int index = 0;
                for(String part : split) {
                    String s = part.trim();
                    if(StringUtils.isBlank(s)) {
                        continue;
                    }

                    List<String> problem;
                    if(problems.size() <= index) {
                        problem = new LinkedList<>();
                        problems.add(problem);
                    } else {
                        problem = problems.get(index);
                    }
                    problem.add(s);
                    index++;
                }

            }
            long total = 0;
            for(List<String> problem : problems) {
                String operation = problem.get(problem.size()-1);
                long result = operation.equals("+") ? 0 : 1;
                for(int i = problem.size()-2; i >= 0; i--) {
                    if(operation.equals("+")) {
                        result += Long.parseLong(problem.get(i));
                    } else {
                        result *= Long.parseLong(problem.get(i));
                    }
                }
                total += result;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
