package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class Day19_2 {
    
    public static class FindResult {
        public final int index;
        public final List<String> molecule;
        
        public FindResult(int index, List<String> molecule) {
            this.index = index;
            this.molecule = molecule;
        }
    }
    
    public static void main(String[] args) {
        try {
            Map<List<String>, String> invertMap = new HashMap<>();
            List<String> finalMolecule = null;
            List<String> lines = AdventUtils.getLines(19);
            boolean mapDone = false;
            for (String line : lines) {
                if(StringUtils.isEmpty(line)) {
                    mapDone = true;
                    continue;
                }
                if(!mapDone) {
                    String atom = StringUtils.substringBefore(line, "=>").trim();
                    List<String> molecule = parseMolecule(StringUtils.substringAfter(line, "=>").trim());
                    invertMap.put(molecule, atom);
                } else {
                    finalMolecule = parseMolecule(line.trim());
                }
            }
            
            List<String> current = finalMolecule;
            int count = 0;
            while(true) {
                List<String> next = replace(current, invertMap);
                if(current.equals(next)) {
                    Validate.isTrue(current.equals(Arrays.asList("e")));
                    break;
                }
                count++;
                current = next;
            }
            System.out.println(count);            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    private static List<String> parseMolecule(String m) {
        List<String> molecule = new ArrayList<>();
        String atom = null;
        for(int i = 0; i < m.length(); i++) {
            char c = m.charAt(i);
            if(c >= 'A' && c <= 'Z') {
                if(atom != null) {
                    molecule.add(atom);
                }
                atom = String.valueOf(c);
            } else {
                atom += c;
            }
        }
        molecule.add(atom);
        return molecule;
    }
    
    private static List<String> replace(List<String> finalMolecule, Map<List<String>, String> invertMap) {
        List<FindResult> results = new LinkedList<>();
        for(int i = 0; i < finalMolecule.size(); i++) {
            for (List<String> molecule : invertMap.keySet()) {
                if(finalMolecule.size() >= i+molecule.size()) {
                    List<String> subMolecule = finalMolecule.subList(i, i+molecule.size());
                    if(subMolecule.equals(molecule)) {
                        results.add(new FindResult(i, molecule));
                    }
                }
            }
        }
        if(results.isEmpty()) {
            return finalMolecule;
        }
        
        FindResult result = results.stream().sorted((r1,r2) -> r2.molecule.size() - r1.molecule.size()).findFirst().orElse(null);
        List<String> newMolecule = new LinkedList<>(finalMolecule);
        for(int j = 0; j < result.molecule.size(); j++) {
            newMolecule.remove(result.index);
        }
        newMolecule.add(result.index, invertMap.get(result.molecule));
        return newMolecule;
    }
}
