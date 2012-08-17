package com.amitk.java.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	public static void main(String[] args) {
		String pattern = "string.*";
		String[] toBeMatched = {"string1","string2","string3","string11", "string111"};
		
		ArrayList matches = new ArrayList();
		
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher("");
		for (int i = 0; i < toBeMatched.length; i++) {
			if (null == toBeMatched[i])
				continue;
			matcher.reset(toBeMatched[i]);
			if(matcher.matches())
				matches.add(toBeMatched[i]);
		}
		
		System.out.println("Matches:\n" + matches);

	}
}
