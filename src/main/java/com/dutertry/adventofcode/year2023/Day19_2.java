package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day19_2 {
    
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
    }
    
    private static class Workflow {
        public final List<Condition> conditions;
        public final String defaultDestination;
        
        public Workflow(List<Condition> conditions, String defaultDestination) {
            this.conditions = conditions;
            this.defaultDestination = defaultDestination;
        }
    }
    
    private static class PartRange {
        public final Map<String, Integer> minValues;
        public final Map<String, Integer> maxValues;
        
        
        public PartRange(Map<String, Integer> minValues, Map<String, Integer> maxValues) {
            this.minValues = minValues;
            this.maxValues = maxValues;
        }

        public boolean isEmpty() {
            return (minValues.get("x") > maxValues.get("x")
                    || minValues.get("m") >  maxValues.get("m")
                    || minValues.get("a") >  maxValues.get("a")
                    || minValues.get("s") >  maxValues.get("s"));
        }
        
        public PartRange applyCondition(Condition condition) {
            if(condition.type.equals(">")) {
                int newMin = condition.value+1;
                int currentMin = minValues.get(condition.propertyName);
                if(newMin > currentMin) {
                    PartRange newPartRange = new PartRange(new HashMap<>(minValues), new HashMap<>(maxValues));
                    newPartRange.minValues.put(condition.propertyName, newMin);
                    return newPartRange;
                }
            } else {
                int newMax = condition.value-1;
                int currentMax = maxValues.get(condition.propertyName);
                if(newMax < currentMax) {
                    PartRange newPartRange = new PartRange(new HashMap<>(minValues), new HashMap<>(maxValues));
                    newPartRange.maxValues.put(condition.propertyName, newMax);
                    return newPartRange;
                }
            }
            return this;
        }
        
        public PartRange applyOppositeCondition(Condition condition) {
            if(condition.type.equals(">")) {
                int newMax = condition.value;
                int currentMax = maxValues.get(condition.propertyName);
                if(newMax < currentMax) {
                    PartRange newPartRange = new PartRange(new HashMap<>(minValues), new HashMap<>(maxValues));
                    newPartRange.maxValues.put(condition.propertyName, newMax);
                    return newPartRange;
                }
            } else {
                int newMin = condition.value;
                int currentMin = minValues.get(condition.propertyName);
                if(newMin > currentMin) {
                    PartRange newPartRange = new PartRange(new HashMap<>(minValues), new HashMap<>(maxValues));
                    newPartRange.minValues.put(condition.propertyName, newMin);
                    return newPartRange;
                }
            }
            return this;
        }
        
        public long getSize() {
            long xsize = maxValues.get("x") - minValues.get("x") + 1;
            long msize = maxValues.get("m") - minValues.get("m") + 1;
            long asize = maxValues.get("a") - minValues.get("a") + 1;
            long ssize = maxValues.get("s") - minValues.get("s") + 1;
            return xsize*msize*asize*ssize;
        }
    }
    
    public static void main(String[] args) {
        try {
            Map<String, Workflow> workflowMap = new HashMap<>();
            
            List<String> lines = AdventUtils.getLines(19, false);
            for (String line : lines) {
                if(StringUtils.isBlank(line)) {
                    break;
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
            }
            
            Workflow in = workflowMap.get("in");
            
            PartRange startRange = new PartRange(
                    Map.of("x", 1, "m", 1, "a", 1, "s", 1),
                    Map.of("x", 4000, "m", 4000, "a", 4000, "s", 4000));
            List<PartRange> acceptedRanges = getAcceptedRanges(startRange, in, workflowMap);
            
            long totalSize = 0;
            for (PartRange partRange : acceptedRanges) {
                totalSize += partRange.getSize();
            }
            System.out.println(totalSize);
            
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
    
    private static List<PartRange> getAcceptedRanges(PartRange partRange, Workflow workflow, Map<String, Workflow> workflowMap) {
        List<PartRange> list = new ArrayList<>();
        for (int i = 0; i < workflow.conditions.size(); i++) {
            Condition condition = workflow.conditions.get(i);
            if(condition.destination.equals("R")) {
                continue;
            }
            
            PartRange nextPartRange = partRange;
            for (int j = 0; j < i; j++) {
                nextPartRange = nextPartRange.applyOppositeCondition(workflow.conditions.get(j));
            }
            
            nextPartRange = nextPartRange.applyCondition(condition);
            if(!nextPartRange.isEmpty()) {
                if(condition.destination.equals("A")) {
                    list.add(nextPartRange);
                } else {
                    List<PartRange> nextRanges = getAcceptedRanges(nextPartRange, workflowMap.get(condition.destination), workflowMap);
                    list.addAll(nextRanges);
                }
            }
        }
        
        if(!workflow.defaultDestination.equals("R")) {
            PartRange lastPartRange = partRange;
            for (int i = 0; i < workflow.conditions.size(); i++) {
                lastPartRange = lastPartRange.applyOppositeCondition(workflow.conditions.get(i));
            }
            if(!lastPartRange.isEmpty()) {
                if(workflow.defaultDestination.equals("A")) {
                    list.add(lastPartRange);
                } else {
                    List<PartRange> nextRanges = getAcceptedRanges(lastPartRange, workflowMap.get(workflow.defaultDestination), workflowMap);
                    list.addAll(nextRanges);
                }
            }
        }
        return list;
    }
}
