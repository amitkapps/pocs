package com.amitk.poc.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class RandomLetters {

	public static void main(String[] args) {
		
//		for (char i = 65; i <= 90; i++) {
//			System.out.print((int)i + "=" + new Character(i) + ", ");
//			
//		}
		
		for (int i = 0; i < 1000000; i++) {
			System.out.println(getRandomUpperCaseString(3));
		}
	}

	public static String getRandomUpperCaseString(int length){
		char[] chars = new char[length]; 
		for (int i = 0; i < length; i++) {
			chars[i] = (char) ((char)(Math.random() * 26) + 65);
		}
		
		return new String(chars);
	}

	
}
