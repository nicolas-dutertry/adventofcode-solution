package com.dutertry.adventofcode.year2015;

public class Day11_2 {
    public static void main(String[] args) {
        long l = unconvert("hxbxwxba");
        long next = l+1;
        while(!isValid(next)) {
            next++;
        }
        next++;
        while(!isValid(next)) {
            next++;
        }
        System.out.println(convert(next));
    }
    
    private static boolean isValid(long l) {
        String s = Long.toString(l, 26);
        char prev = '-';
        char prevPrev = '-';
        boolean straight = false;
        int doubleCount = 0;
        int lastDblIdx = -1;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '8' || c == 'b' || c == 'e') {
                return false;
            }
            if(prev == c-1 && prevPrev == c-2) {
                straight = true;
            }
            
            if(prev == c && lastDblIdx != i-2) {
                doubleCount++;
                lastDblIdx = i-1;
            }
            
            prevPrev = prev;
            prev = c;
        }
        
        return straight && doubleCount >= 2;
    }
    
    private static String convert(long l) {
        String s = Long.toString(l, 26);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            builder.append(convert(s.charAt(i)));
        }
        return builder.toString();
    }
    
    private static long unconvert(String s) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            builder.append(unconvert(s.charAt(i)));
        }
        return Long.parseLong(builder.toString(), 26);
    }
    
    private static char convert(char c) {
        if(c >= '0' && c <= '9') {
            return (char)(c - '0' + 'a');
        } else {
            return (char)(c - 'a' + 'k');
        }
    }
    
    private static char unconvert(char c) {
        if(c >= 'a' && c <= 'j') {
            return (char)(c - 'a' + '0');
        } else {
            return (char)(c - 'k' + 'a');
        }
    }
}
