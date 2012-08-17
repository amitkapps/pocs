package com.amitk.java.regex;

import java.util.ArrayList;

import org.apache.oro.text.GlobCompiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Pattern;

public class GlobPatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String patternString = "string1?";
		String[] toBeMatched = {"string1","string2","string3","string11", "string111"};
		
		ArrayList matches = new ArrayList();
		
		Perl5Pattern pattern = (Perl5Pattern) new GlobCompiler().compile(patternString);
		
		 Perl5Matcher matcher = new Perl5Matcher();
		for (int i = 0; i < toBeMatched.length; i++) {
			if (null == toBeMatched[i])
				continue;
			if( matcher.matches(toBeMatched[i], pattern) )
				matches.add(toBeMatched[i]);
		}
		
		System.out.println("Matches:\n" + matches);
		

	}

}
