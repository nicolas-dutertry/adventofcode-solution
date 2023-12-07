package com.dutertry.adventofcode.year2015;

import org.apache.commons.codec.digest.DigestUtils;

public class Day4_2 {

	public static void main(String[] args) {
		int i = 0;
		while(true) {
			String md5Hex = DigestUtils.md5Hex("yzbqklnj" + i);
			if(md5Hex.startsWith("000000")) {
				System.out.println(i);
				break;
			}
			i++;
		}
		
	}

}
