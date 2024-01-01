package com.dutertry.adventofcode.year2015;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day22_2 {
    private static class State {
        private int mana;
        private int spentMana;
        private int hitPoints;
        private int armor;
        private int opponentHitPoints;
        private final Map<String, Integer> counters = new HashMap<>();
        
        public State(int mana, int spentMana, int hitPoints, int armor, int opponentHitPoints) {
            this.mana = mana;
            this.spentMana = spentMana;
            this.hitPoints = hitPoints;
            this.armor = armor;
            this.opponentHitPoints = opponentHitPoints;
        }
        
        public State copy() {
            State newState = new State(mana, spentMana, hitPoints, armor, opponentHitPoints);
            newState.counters.putAll(counters);
            return newState;
        }
        
        public boolean isWin() {
            return opponentHitPoints <= 0;
        }
        
        public boolean isLose() {
            return hitPoints <= 0;
        }
    }
    
    private static abstract class Spell {
        private final String name;
        private final int cost;
        private final int turns;

        public Spell(String name ,int cost, int turns) {
            this.name = name;
            this.cost = cost;
            this.turns = turns;
        }
        
        public abstract void apply(State state);
    }
    
    private static class MagicMissile extends Spell {
        public MagicMissile(String name) {
            super(name, 53, 1);
        }
        
        public void apply(State state) {
            state.opponentHitPoints -= 4;
        }
    }
    
    private static class Drain extends Spell {
        public Drain(String name) {
            super(name, 73, 1);
        }

        public void apply(State state) {
            state.hitPoints += 2;
            state.opponentHitPoints -= 2;
        }
    }
    
    private static class Shield extends Spell {
        public Shield(String name) {
            super(name, 113, 6);
        }
        
        public void apply(State state) {
            state.armor = 7;
        }
    }
    
    private static class Poison extends Spell {
        public Poison(String name) {
            super(name, 173, 6);
        }

        public void apply(State state) {
            state.opponentHitPoints -= 3;
        }
    }
    
    private static class Recharge extends Spell {
        public Recharge(String name) {
            super(name, 229, 5);
        }

        public void apply(State state) {
            state.mana += 101;
        }
    }
    
    private static final int START_HIT_POINT = 50;
    private static final int START_MANA = 500;
    private static final int OPPONENT_HIT_POINT = 51;
    private static final int OPPONENT_DAMAGE = 9;
    private static Map<String, Spell> spellMap = Map.of(
            "MagicMissile", new MagicMissile("MagicMissile"),
            "Drain", new Drain("Drain"),
            "Shield", new Shield("Shield"),
            "Poison", new Poison("Poison"),
            "Recharge", new Recharge("Recharge")
            );
    
    
    public static void main(String[] args) {
        int minWinMana = Integer.MAX_VALUE;
        State startState = new State(START_MANA, 0, START_HIT_POINT-1, 0, OPPONENT_HIT_POINT);
        List<State> states = Collections.singletonList(startState);
        while(!states.isEmpty()) {
            List<State> nextstates = getNextPossibleStates(states, minWinMana);
            Iterator<State> it = nextstates.iterator();
            while(it.hasNext()) {
                State nextstate = it.next();
                applyStateSpells(nextstate);
                if(nextstate.isWin()) {
                    if(nextstate.spentMana < minWinMana) {
                        minWinMana = nextstate.spentMana;
                    }
                    it.remove();
                    continue;
                }
                nextstate.hitPoints -= OPPONENT_DAMAGE - nextstate.armor;
                nextstate.armor = 0;
                if(nextstate.isLose()) {
                    it.remove();
                    continue;
                }
                
                nextstate.hitPoints -= 1;
                if(nextstate.isLose()) {
                    it.remove();
                    continue;
                }
                applyStateSpells(nextstate);
                nextstate.armor = 0;
                if(nextstate.isWin()) {
                    if(nextstate.spentMana < minWinMana) {
                        minWinMana = nextstate.spentMana;
                    }
                    it.remove();
                    continue;
                }
            }
            states = nextstates;
        }
        System.out.println(minWinMana);
    }
    
    private static void applyStateSpells(State nextstate) {
        Iterator<Entry<String, Integer>> counterIt = nextstate.counters.entrySet().iterator();
        while(counterIt.hasNext()) {
            Entry<String, Integer> entry = counterIt.next();
            String spellName = entry.getKey();
            int counter = entry.getValue();
            Spell spell = spellMap.get(spellName);
            spell.apply(nextstate);
            counter--;
            if(counter == 0) {
                counterIt.remove();
            } else {
                entry.setValue(counter);
            }
        }
    }
    
    private static List<State> getNextPossibleStates(List<State> states, int minWinMana) {
        List<State> possibleStates = new LinkedList<>();
        for (State state : states) {
            for (Spell spell : spellMap.values()) {
                if(state.counters.containsKey(spell.name)) {
                    continue;
                }
                if(state.mana < spell.cost) {
                    continue;
                }
                
                State newState = state.copy();
                newState.mana -= spell.cost;
                newState.spentMana += spell.cost;
                if(newState.spentMana < minWinMana) {
                    newState.counters.put(spell.name, spell.turns);
                    possibleStates.add(newState);
                }
            }
        }
        return possibleStates;
    }
}
