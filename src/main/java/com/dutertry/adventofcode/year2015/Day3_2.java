package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day3_2 {
	private static class Pos {
		public int x;
		public int y;
		
		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pos other = (Pos) obj;
			return x == other.x && y == other.y;
		}
		
	}

	public static void main(String[] args) {
		try {
			String input = AdventUtils.getInputAsString(3);
			
			Set<Pos> positions = new HashSet<Pos>();
			int santax = 0;
			int santay = 0;
			int robotx = 0;
			int roboty = 0;
			positions.add(new Pos(santax, santay));
			positions.add(new Pos(robotx, roboty));
			for(int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				if(i % 2 == 0) {
					if(c == '>') {
						santax++;
					} else if(c == '<') {
						santax--;
					} else if(c == '^') {
						santay++;
					} else if(c == 'v') {
						santay--;
					}
					positions.add(new Pos(santax, santay));
				} else {
					if(c == '>') {
						robotx++;
					} else if(c == '<') {
						robotx--;
					} else if(c == '^') {
						roboty++;
					} else if(c == 'v') {
						roboty--;
					}
					positions.add(new Pos(robotx, roboty));
				}
			}
			System.out.println(positions.size());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
