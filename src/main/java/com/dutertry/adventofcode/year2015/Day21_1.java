package com.dutertry.adventofcode.year2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day21_1 {
    private static class Item {
        private final String name;
        private final int cost;
        private final int damage;
        private final int armor;
        
        public Item(String name, int cost, int damage, int armor) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }
    }
    
    private static final int HIT_POINT = 100;
    private static final int OPPONENT_HIT_POINT = 104;
    private static final int OPPONENT_DAMAGE = 8;
    private static final int OPPONENT_ARMOR = 1;
    
    
    
    public static void main(String[] args) {
        List<Item> weapons = Arrays.asList(
                new Item("Dagger", 8, 4, 0),
                new Item("Shortsword", 10, 5, 0),
                new Item("Warhammer", 25, 6, 0),
                new Item("Longsword", 40, 7, 0),
                new Item("Greataxe", 74, 8, 0)
                );
        
        List<Item> armors = Arrays.asList(
                new Item("Null", 0, 0, 0),
                new Item("Leather", 13, 0, 1),
                new Item("Chainmail", 31, 0, 2),
                new Item("Splintmail", 53, 0, 3),
                new Item("Bandedmail", 75, 0, 4),
                new Item("Platemail", 102, 0, 5)
                );
        
        List<Item> rings = Arrays.asList(
                new Item("Null 1", 0, 0, 0),
                new Item("Null 2", 0, 0, 0),
                new Item("Damage +1", 25, 1, 0),
                new Item("Damage +2", 50, 2, 0),
                new Item("Damage +3", 100, 3, 0),
                new Item("Defense +1", 20, 0, 1),
                new Item("Defense +2", 40, 0, 2),
                new Item("Defense +3", 80, 0, 3)
                );
        
        List<List<Item>> possibleItems = getPossibleItems(weapons, armors, rings);
        int minWinCost = Integer.MAX_VALUE;
        for (List<Item> items : possibleItems) {
            int damage = getDamage(items);
            int armor = getArmor(items);
            int cost = getCost(items);
            
            int opponentLoss = damage-OPPONENT_ARMOR;
            if(opponentLoss < 1) {
                opponentLoss = 1;
            }
            int opponentLoseSteps = OPPONENT_HIT_POINT / opponentLoss;
            if(OPPONENT_HIT_POINT % opponentLoss != 0) {
                opponentLoseSteps++;
            }
            
            int loss = OPPONENT_DAMAGE-armor;
            if(loss < 1) {
                loss = 1;
            }
            int loseSteps = HIT_POINT / loss;
            if(HIT_POINT % loss != 0) {
                loseSteps++;
            }
            
            if(opponentLoseSteps <= loseSteps) {
                if(cost < minWinCost) {
                    minWinCost = cost;
                }
            }
        }
        System.out.println(minWinCost);
    }
    
    private static List<List<Item>> getPossibleItems(List<Item> weapons, List<Item> armors, List<Item> rings) {
        List<List<Item>> possibleItems = new LinkedList<>();
        for (Item weapon : weapons) {
            for (Item armor : armors) {
                for(int i = 0; i < rings.size(); i++) {
                    Item ring1 = rings.get(i);
                    for(int j = i+1; j < rings.size(); j++) {
                        Item ring2 = rings.get(j);
                        List<Item> possibleItem = new ArrayList<>(4);
                        possibleItem.add(weapon);
                        possibleItem.add(armor);
                        possibleItem.add(ring1);
                        possibleItem.add(ring2);
                        possibleItems.add(possibleItem);
                    }
                }
            }
        }
        return possibleItems;
    }
    
    private static int getDamage(List<Item> items) {
        int damage = 0;
        for (Item item : items) {
            damage += item.damage;
        }
        return damage;
    }
    
    private static int getArmor(List<Item> items) {
        int armor = 0;
        for (Item item : items) {
            armor += item.armor;
        }
        return armor;
    }
    
    private static int getCost(List<Item> items) {
        int cost = 0;
        for (Item item : items) {
            cost += item.cost;
        }
        return cost;
    }
}
