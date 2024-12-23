package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Day23_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(23)) {
            long total = 0;

            Set<Set<String>> connections = new HashSet<>();
            Set<String> computers = new HashSet<>();

            String line;
            while((line = br.readLine()) != null ) {
                String[] array = StringUtils.split(line, "-");
                String c1 = array[0];
                String c2 = array[1];
                connections.add(Set.of(c1, c2));
                computers.add(c1);
                computers.add(c2);
            }

            Set<Set<String>> lans = new HashSet<>();
            for(Set<String> connection : connections) {
                Iterator<String> iterator = connection.iterator();
                String c1 = iterator.next();
                String c2 = iterator.next();
                for(String computer : computers) {
                    if(computer.equals(c1) || computer.equals(c2)) {
                        continue;
                    }

                    Set<String> con1 = Set.of(c1, computer);
                    Set<String> con2 = Set.of(c2, computer);

                    if(connections.contains(con1) && connections.contains(con2)) {
                        lans.add(Set.of(c1, c2, computer));
                    }
                }
            }

            for (Set<String> lan : lans) {
                for(String c : lan) {
                    if(c.startsWith("t")) {
                        total++;
                        break;
                    }
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
