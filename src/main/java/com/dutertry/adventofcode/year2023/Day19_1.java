package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day19_1 {
    private static class Part {
        private final Map<String, Integer> properties;

        public Part(Map<String, Integer> properties) {
            this.properties = properties;
        }
        
        public int getProperty(String name) {
            return properties.get(name);
        }
        
        public int getTotal() {
            int total = 0;
            for (int value : properties.values()) {
                total += value;
            }
            return total;
        }
    }
    
    private static class Condition {
        public final String propertyName;
        public final String type;
        public final int value;
        public final String destination;
        
        public Condition(String propertyName, String type, int value, String destination) {
            this.propertyName = propertyName;
            this.type = type;
            this.value = value;
            this.destination = destination;
        }
        
        public boolean eval(Part part) {
            int partValue = part.getProperty(propertyName);
            if(type.equals(">")) {
                return partValue > value;
            } else {
                return partValue < value;
            }
        }
    }
    
    private static class Workflow {
        public final List<Condition> conditions;
        public final String defaultDestination;
        
        public Workflow(List<Condition> conditions, String defaultDestination) {
            this.conditions = conditions;
            this.defaultDestination = defaultDestination;
        }
        
        public String getDestination(Part part) {
            for (Condition condition : conditions) {
                if(condition.eval(part)) {
                    return condition.destination;
                }
            }
            return defaultDestination;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<Part> parts = null;
            Map<String, Workflow> workflowMap = new HashMap<>();
            
            List<String> lines = AdventUtils.getLines(19, false);
            for (String line : lines) {
                if(parts == null) {
                    if(StringUtils.isBlank(line)) {
                        parts = new ArrayList<>();
                        continue;
                    }
                    
                    String workflowName = StringUtils.substringBefore(line, "{");
                    String s = StringUtils.substringBefore(StringUtils.substringAfter(line, "{"), "}");
                    String[] array = StringUtils.split(s, ",");
                    List<Condition> conditions = new ArrayList<>();
                    for (int i = 0; i < array.length-1; i++) {
                        conditions.add(parseCondition(array[i]));
                    }
                    String defaultDestination = array[array.length-1];
                    workflowMap.put(workflowName, new Workflow(conditions, defaultDestination));
                } else {
                    String s = StringUtils.substringBefore(StringUtils.substringAfter(line, "{"), "}");
                    Map<String, Integer> properties = new HashMap<>();
                    String[] array = StringUtils.split(s, ",");
                    for (String p : array) {
                        String name = StringUtils.substringBefore(p, "=");
                        Integer value = Integer.parseInt(StringUtils.substringAfter(p, "="));
                        properties.put(name, value);
                    }
                    parts.add(new Part(properties));
                }
            }
            
            List<Part> acceptedParts = new LinkedList<>();
            Workflow in = workflowMap.get("in");
            for (Part part : parts) {
                Workflow workflow = in;
                boolean accepted;
                while(true) {
                    String destination = workflow.getDestination(part);
                    if(destination.equals("R")) {
                        accepted = false;
                        break;
                    }
                    if(destination.equals("A")) {
                        accepted = true;
                        break;
                    }
                    workflow = workflowMap.get(destination);
                }
                if(accepted) {
                    acceptedParts.add(part);
                }
            }
            
            int total = 0;
            for (Part part : acceptedParts) {
                total += part.getTotal();
            }
            System.out.println(total);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Condition parseCondition(String s) {
        String[] array = StringUtils.split(s, ":<>");
        String propertyName = array[0];
        String type = s.contains(">") ? ">" : "<";
        int value = Integer.parseInt(array[1]);
        String destination = array[2];
        
        return new Condition(propertyName, type, value, destination);
    }
}
