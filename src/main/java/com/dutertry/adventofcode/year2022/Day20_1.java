package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day20_1 {
    private static record MixNumber(int initialPosition, int value) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(20)) {
            List<MixNumber> numbers = new LinkedList<>();
            String line;
            int index = 0;
            while((line = br.readLine()) != null) {
                numbers.add(new MixNumber(index, Integer.parseInt(line)));
                index++;
            }

            List<MixNumber> currentNumbers = new LinkedList<>(numbers);
            int size = numbers.size();
            for (MixNumber number : numbers) {
                int value = number.value;
                int currentIndex = currentNumbers.indexOf(number);
                int newIndex = modulus(currentIndex + value, size - 1);
                if (newIndex == 0) {
                    newIndex = size - 1;
                }
                currentNumbers.remove(currentIndex);
                currentNumbers.add(newIndex, number);
            }

            int zeroIndex  = 0;
            for(int i = 0; i < currentNumbers.size(); i++) {
                MixNumber number = currentNumbers.get(i);
                if(number.value == 0) {
                    zeroIndex = i;
                    break;
                }
            }

            int n1000 = currentNumbers.get(modulus(zeroIndex + 1000, size)).value;
            int n2000 = currentNumbers.get(modulus(zeroIndex + 2000, size)).value;
            int n3000 = currentNumbers.get(modulus(zeroIndex + 3000, size)).value;

            System.out.println(n1000 + n2000 + n3000);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public static int modulus(int a, int b) {
        return (a % b + b) % b;
    }
}
