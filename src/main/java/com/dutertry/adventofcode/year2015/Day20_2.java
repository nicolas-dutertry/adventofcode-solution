package com.dutertry.adventofcode.year2015;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day20_2 {
    private static final long PRESENT_COUNT = 29_000_000;
    
    public static void main(String[] args) {
        List<Integer> primeNumbers = generatePrimeNumbers(2_000_000);
        int[] houseCounts = new int[2_000_000];
        
        int house = 2;
        while(true) {
            Set<Integer> divisors = getDivisors(house, primeNumbers);
            
            long housePresentCount = 0;
            if(houseCounts[1] < 50) {
                houseCounts[1]++;
                housePresentCount += 11;
            }
            for (Integer divisor : divisors) {
                if(houseCounts[divisor] < 50) {
                    houseCounts[divisor]++;
                    housePresentCount += 11 * divisor;
                }
            }
            
            if(housePresentCount >= PRESENT_COUNT ) {
                System.out.println(house);
                break;
            }
            house++;
        }
	}
    
    private static List<Integer> generatePrimeNumbers(int limit) {
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        List<Integer> primeNumbers = new ArrayList<>(limit);
        for (int i = 2; i <= limit; i++) {
            if(isPrime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
    
    private static List<Integer> getPrimeDecomposition(int number, List<Integer> primeNumbers) {
        List<Integer> decomposition = new LinkedList<>();
        int temp = number;
        for(int i = 0; i <= primeNumbers.size(); i++) {
            int prime = primeNumbers.get(i);
            while(temp % prime == 0) {
                decomposition.add(prime);
                temp = temp / prime;  
            }
            if(temp == 1) {
                break;
            }
        }
        return decomposition;
    }
    
    private static Set<Integer> getDivisors(int number, List<Integer> primeNumbers) {
        List<Integer> primeDecomposition = getPrimeDecomposition(number, primeNumbers);
        return getDivisors(primeDecomposition);
    }
    
    private static Set<Integer> getDivisors(List<Integer> primeDecomposition) {
        if(primeDecomposition.size() == 1) {
            Set<Integer> divisors = new HashSet<>(primeDecomposition);
            return divisors;
        }
        int first = primeDecomposition.get(0);
        Set<Integer> subDivisors = getDivisors(primeDecomposition.subList(1, primeDecomposition.size()));
        Set<Integer> divisors = new HashSet<>(subDivisors);
        divisors.add(first);
        for (int divisor : subDivisors) {
            divisors.add(divisor*first);
        }
        return divisors;
    }
    
}
