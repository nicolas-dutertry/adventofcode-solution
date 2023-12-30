package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Day19_1 {
    
    public static void main(String[] args) {
        try {
            Map<String, List<List<String>>> transformMap = new HashMap<>();
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
                    List<List<String>> molecules = transformMap.get(atom);
                    if(molecules == null) {
                        molecules = new ArrayList<>();
                        transformMap.put(atom, molecules);
                    }
                    molecules.add(molecule);
                } else {
                    finalMolecule = parseMolecule(line.trim());
                }
            }
            
            Set<List<String>> transformedMolecules = new HashSet<>();
            for (int i = 0; i < finalMolecule.size(); i++) {
                String atom = finalMolecule.get(i);
                List<List<String>> molecules = transformMap.get(atom);
                if(molecules == null) {
                    continue;
                }
                for (List<String> molecule : molecules) {
                    List<String> newMolecule = replaceAtom(finalMolecule, i, molecule);
                    transformedMolecules.add(newMolecule);
                }
            }
            System.out.println(transformedMolecules.size());
            
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
    
    private static List<String> replaceAtom(List<String> molecule, int index, List<String> replacement) {
        List<String> newMolecule = new LinkedList<>(molecule);
        newMolecule.remove(index);
        newMolecule.addAll(index, replacement);
        return newMolecule;
    }
}
