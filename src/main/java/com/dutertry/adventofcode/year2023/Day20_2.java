package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day20_2 {
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
        public final Set<String> inputs = new HashSet<>();
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
            inputs.add(input);
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
        public Map<String, Pulse> receivedPulses = new LinkedHashMap<>();
        
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
            
            String beforeRx = null;
            for (Entry<String, Module> entry : moduleMap.entrySet()) {
                String moduleName = entry.getKey();
                Module module = entry.getValue();
                List<String> outputs = module.getOutputs();
                for (String output : outputs) {
                    Module outputModule = moduleMap.get(output);
                    if(outputModule != null) {
                        outputModule.addInput(moduleName);
                    }
                    if(output.equals("rx")) {
                        beforeRx = moduleName;
                    }
                }
            }
            
            Module broadcaster = moduleMap.get(BROADCASTER);
            List<Long> numbers = new ArrayList<>();
            for(String entryPoint : broadcaster.getOutputs()) {
                numbers.add(getButtonCount(entryPoint, beforeRx, moduleMap));
            }
            
            long lcm = lcm(numbers);
            System.out.println(lcm);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long getButtonCount(String entryInput, String beforeRx, Map<String, Module> moduleMap) {
        long buttonCount = 0;
        while(true) {
            buttonCount++;
            List<PulseAction> pulseActions = new LinkedList<>();
            pulseActions.add(new PulseAction(BROADCASTER, Pulse.LOW, entryInput));
            while(!pulseActions.isEmpty()) {
                List<PulseAction> nextPulseActions = new LinkedList<>();
                for (PulseAction pulseAction : pulseActions) {
                    if(pulseAction.pulse == Pulse.HIGH && pulseAction.output.equals(beforeRx)) {
                        return buttonCount;
                    }
                    
                    Module module = moduleMap.get(pulseAction.output);
                    if(module != null) {
                        nextPulseActions.addAll(module.receive(pulseAction.pulse, pulseAction.input));
                    }
                }
                pulseActions = nextPulseActions;
            }
        }
    }
    
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long lcm(List<Long> numbers) {
        long result = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }

        return result;
    }
}
