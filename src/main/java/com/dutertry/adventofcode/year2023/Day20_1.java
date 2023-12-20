package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day20_1 {
    private static final String BROADCASTER = "broadcaster";
    
    private enum Pulse {
        HIGH,
        LOW
    }
    
    private static class PulseAction {
        public final String input;
        public final Pulse pulse;
        public final String output;
        
        public PulseAction(String input, Pulse pulse, String output) {
            this.input = input;
            this.pulse = pulse;
            this.output = output;
        }
    }
    
    private static interface Module {
        String getName();
        List<String> getOutputs();
        void addInput(String input);
        List<PulseAction> receive(Pulse pulse, String input);
    }
    
    private static class FliFlopModule implements Module {
        public final String name;
        public final List<String> outputs;
        public boolean on = false;
        
        public FliFlopModule(String name, List<String> outputs) {
            this.name = name;
            this.outputs = outputs;
        }
        
        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<String> getOutputs() {
            return outputs;
        }

        public void addInput(String input) {
        }
        
        public List<PulseAction> receive(Pulse pulse, String origin) {
            List<PulseAction> pulseActions = new LinkedList<>();
            
            if(pulse == Pulse.LOW) {
                Pulse nextPulse;
                on = !on;
                if(on) {
                    nextPulse = Pulse.HIGH;
                } else {
                    nextPulse = Pulse.LOW;
                }
                
                for (String output : outputs) {
                    pulseActions.add(new PulseAction(name, nextPulse, output));
                }
            }
            
            return pulseActions;
        }
    }
    
    private static class ConjunctionModule implements Module {
        public final String name;
        public final List<String> outputs;
        public Map<String, Pulse> receivedPulses = new HashMap<>();
        
        public ConjunctionModule(String name, List<String> outputs) {
            this.name = name;
            this.outputs = outputs;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
        @Override
        public List<String> getOutputs() {
            return outputs;
        }

        public void addInput(String input) {
            receivedPulses.put(input, Pulse.LOW);
        }
        
        public List<PulseAction> receive(Pulse pulse, String input) {
            receivedPulses.put(input, pulse);
            List<PulseAction> pulseActions = new LinkedList<>();
            
            Pulse nextPulse = Pulse.LOW;
            for (Pulse p : receivedPulses.values()) {
                if(p == Pulse.LOW) {
                    nextPulse = Pulse.HIGH;
                    break;
                }
            }
            
            for (String output : outputs) {
                pulseActions.add(new PulseAction(name, nextPulse, output));
            }
            
            return pulseActions;
        }
    }
    
    private static class BroadcasterModule implements Module {
        public final List<String> outputs;
        
        public BroadcasterModule(List<String> outputs) {
            this.outputs = outputs;
        }
        
        @Override
        public String getName() {
            return BROADCASTER;
        }

        @Override
        public List<String> getOutputs() {
            return outputs;
        }

        @Override
        public void addInput(String input) {            
        }

        @Override
        public List<PulseAction> receive(Pulse pulse, String input) {
            List<PulseAction> pulseActions = new LinkedList<>();
            for (String output : outputs) {
                pulseActions.add(new PulseAction(BROADCASTER, pulse, output));
            }
            return pulseActions;
        }
        
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(20, false);
            Map<String, Module> moduleMap = new LinkedHashMap<>();
            for (String line : lines) {
                String moduleName = StringUtils.substringBefore(line, "->").trim();
                String[] array = StringUtils.split(StringUtils.substringAfter(line, "->").trim(), ",");
                List<String> outputs = Arrays.asList(array).stream().map(String::trim).collect(Collectors.toList());
                if(moduleName.equals(BROADCASTER)) {
                    moduleMap.put(BROADCASTER, new BroadcasterModule(outputs));
                } else if(moduleName.startsWith("%")) {
                    String name = moduleName.substring(1);
                    moduleMap.put(name, new FliFlopModule(name, outputs));
                } else if(moduleName.startsWith("&")) {
                    String name = moduleName.substring(1);
                    moduleMap.put(name, new ConjunctionModule(name, outputs));
                } else {
                    throw new RuntimeException("Oups");
                }
            }
            
            for (Entry<String, Module> entry : moduleMap.entrySet()) {
                String moduleName = entry.getKey();
                Module module = entry.getValue();
                List<String> outputs = module.getOutputs();
                for (String output : outputs) {
                    Module outputModule = moduleMap.get(output);
                    if(outputModule != null) {
                        outputModule.addInput(moduleName);
                    }
                }
            }
            
            long lowcount = 0;
            long highcount = 0;
            
            for(int i = 0; i < 1000; i++) {
                List<PulseAction> pulseActions = new LinkedList<>();
                pulseActions.add(new PulseAction("button", Pulse.LOW, BROADCASTER));
                while(!pulseActions.isEmpty()) {
                    List<PulseAction> nextPulseActions = new LinkedList<>();
                    for (PulseAction pulseAction : pulseActions) {
                        if(pulseAction.pulse == Pulse.LOW) {
                            lowcount++;
                        } else {
                            highcount++;
                        }
                        
                        Module module = moduleMap.get(pulseAction.output);
                        if(module != null) {
                            nextPulseActions.addAll(module.receive(pulseAction.pulse, pulseAction.input));
                        }
                    }
                    pulseActions = nextPulseActions;
                }
            }
            
            System.out.println(lowcount*highcount);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
