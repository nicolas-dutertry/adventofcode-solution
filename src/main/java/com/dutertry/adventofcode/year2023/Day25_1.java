package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day25_1 {    
    private static class Wire {
        public final String module1;
        public final String module2;
        
        public Wire(String module1, String module2) {
            if(module1.compareTo(module2) < 0) {
                this.module1 = module1;
                this.module2 = module2;
            } else {
                this.module1 = module2;
                this.module2 = module1;
            }
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(module1, module2);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Wire other = (Wire) obj;
            return Objects.equals(module1, other.module1) && Objects.equals(module2, other.module2);
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(25);
            Map<String, Set<String>> componentMap = new TreeMap<>();
            Set<Wire> wires = new HashSet<>();
            for (String line : lines) {
                String[] array = StringUtils.split(line);
                String main = null;
                Set<String> others = new HashSet<>();
                for(int i = 0; i < array.length; i++) {
                    if(i == 0) {
                        main = StringUtils.replace(array[i], ":", "").trim();
                    } else {
                        others.add(array[i].trim());
                    }
                }
                
                Set<String> mainConnections = componentMap.get(main);
                if(mainConnections == null) {
                    mainConnections = new TreeSet<>();
                    componentMap.put(main, mainConnections);
                }
                mainConnections.addAll(others);
                
                for (String other : others) {
                    Set<String> otherConnections = componentMap.get(other);
                    if(otherConnections == null) {
                        otherConnections = new TreeSet<>();
                        componentMap.put(other, otherConnections);
                    }
                    otherConnections.add(main);
                    wires.add(new Wire(main, other));
                }
            }
            
            Map<Wire, Integer> wireWeights = new HashMap<>(); 
            for (Wire wire : wires) {
                wireWeights.put(wire, getWeight(wire, componentMap));
            }
            
            List<Wire> sortedWires = wires.stream().sorted((w1, w2) -> {
                return wireWeights.get(w2) - wireWeights.get(w1);
            }).collect(Collectors.toList());
            
            Set<Wire> forbiddenWires = new HashSet<>();
            forbiddenWires.add(sortedWires.get(0));
            forbiddenWires.add(sortedWires.get(1));
            forbiddenWires.add(sortedWires.get(2));
            
            String startPoint = componentMap.keySet().iterator().next();
            Set<String> g1 = getAccessiblePoints(startPoint, componentMap, forbiddenWires);
            Set<String> g2 = new HashSet<>(componentMap.keySet());
            g2.removeAll(g1);
            
            System.out.println((long)g1.size() * (long)g2.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Set<String> getAccessiblePoints(String accessPoint, Map<String, Set<String>> accessConnectionMap, Set<Wire> forbiddenWires) {
        Set<String> done = new HashSet<>();
        done.add(accessPoint);
        computeAccessiblePoints(accessPoint, accessConnectionMap, done, forbiddenWires);
        return done;
    }
    
    private static void computeAccessiblePoints(String accessPoint, Map<String, Set<String>> accessConnectionMap, Set<String> done, Set<Wire> forbiddenWires) {
        Set<String> points = accessConnectionMap.get(accessPoint);
        for (String otherAccess : points) {
            Wire wire = new Wire(accessPoint, otherAccess);
            if(!done.contains(otherAccess) && !forbiddenWires.contains(wire)) {
                done.add(otherAccess);
                computeAccessiblePoints(otherAccess, accessConnectionMap, done, forbiddenWires);
            }
        }
    }
    
    private static int getWeight(Wire wire, Map<String, Set<String>> accessConnectionMap) {
        Set<Wire> forbiddenWires = Collections.singleton(wire);
        int step = 1;
        Set<String> points = Collections.singleton(wire.module1);
        Set<String> done = new HashSet<>();
        done.add(wire.module1);
        while(true) {
            Set<String> directAccessiblePoints = getDirectAccessiblePoints(points, accessConnectionMap, done, forbiddenWires);
            if(directAccessiblePoints.isEmpty()) {
                return -1;
            }
            if(directAccessiblePoints.contains(wire.module2)) {
                return step;
            }
            points = directAccessiblePoints;
            step++;
        }
    }
    
    private static Set<String> getDirectAccessiblePoints(Set<String> points, Map<String, Set<String>> accessConnectionMap, Set<String> done, Set<Wire> forbiddenWires) {
        Set<String> accessiblePoints = new HashSet<>();
        for (String point : points) {
            Set<String> otherPoints = accessConnectionMap.get(point);
            for (String otherPoint : otherPoints) {
                Wire wire = new Wire(point, otherPoint);
                if(!done.contains(otherPoint) && !forbiddenWires.contains(wire)) {
                    done.add(otherPoint);
                    accessiblePoints.add(otherPoint);
                }
            }
        }
        return accessiblePoints;
    }
}
