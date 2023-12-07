package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day7_2 {
    private enum Type {
        FIVE(6),
        FOUR(5),
        FULL_HOUSE(4),
        THREE(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGHT_CARD(0);
        
        private final int score;
        
        private Type(int score) {
            this.score = score;
        }
        
        public int getScore() {
            return score;
        }
    }
    
    private static class Hand {
        private final String cards;
        private final long bid;
        private final Type type;
        private final int score;
        
        public Hand(String cards, long bid) {
            this.cards = cards;
            this.bid = bid;
            Map<Character, Integer> cardMap = new HashMap<>();
            int jCount = 0;
            for (int i = 0; i < cards.length(); i++) {
                char c = cards.charAt(i);
                if(c == 'J') {
                    jCount++;
                } else {
                    Integer count = cardMap.get(c);
                    if(count == null) {
                        count = 0;
                    }
                    cardMap.put(c, count+1);
                }
            }
            if(jCount == 5) {
                type = Type.FIVE;
            } else {
                List<Integer> counts = new LinkedList<>(cardMap.values());
                counts.sort((i1,i2) -> i2-i1);
                
                if(counts.get(0)+jCount == 5) {
                    type = Type.FIVE;
                } else if(counts.get(0)+jCount == 4) {
                    type = Type.FOUR;
                } else if(counts.get(0)+jCount == 3 && counts.get(1) == 2) {
                    type = Type.FULL_HOUSE;
                } else if(counts.get(0)+jCount == 3) {
                    type = Type.THREE;
                } else if(counts.get(0)+jCount == 2  && counts.get(1) == 2) {
                    type = Type.TWO_PAIR;
                } else if(counts.get(0)+jCount == 2) {
                    type = Type.ONE_PAIR;
                } else {
                    type = Type.HIGHT_CARD;
                }
            }
            
            score = getCardScore(cards.charAt(4)) +
                    100 * getCardScore(cards.charAt(3)) +
                    10000 * getCardScore(cards.charAt(2)) + 
                    1000000 * getCardScore(cards.charAt(1)) +
                    100000000 * getCardScore(cards.charAt(0));
            
        }

        public String getCards() {
            return cards;
        }

        public long getBid() {
            return bid;
        }

        public Type getType() {
            return type;
        }

        public int getScore() {
            return score;
        }
    }
    
    private static int getCardScore(char card) {
        if(card >= '2' && card <= '9') {
            return Integer.parseInt(String.valueOf(card));
        } else if(card == 'T') {
            return 10;
        } else if(card == 'J') {
            return 1;
        } else if(card == 'Q') {
            return 12;
        } else if(card == 'K') {
            return 13;
        } else if(card == 'A') {
            return 14;
        }
        throw new RuntimeException("Unknown card " + card);
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(7)) {
            
            List<Hand> hands = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }
                 String[] array = line.split(" ");
                 String cards = array[0];
                 long bid = Long.parseLong(array[1]);
                 Hand hand = new Hand(cards, bid);
                 hands.add(hand);
            }
            
            hands.sort((h1, h2) -> {
                int typeScore = h1.getType().getScore() - h2.getType().getScore();
                if(typeScore != 0) {
                    return typeScore;
                }
                return h1.getScore() - h2.getScore();
            });
            
            long total = 0;
            int rank = 1;
            for (Hand hand : hands) {
                total += rank*hand.getBid();
                rank++;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
