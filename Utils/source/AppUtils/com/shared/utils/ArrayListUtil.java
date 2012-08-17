package com.shared.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ArrayListUtil {
	private static Logger logger = Logger.getLogger(ArrayListUtil.class);
	
	/**
	 * returns the ArrayList as a single string 
	 * comprising of the arraylist elements separated by char delimiter
	 * @param arrayList
	 * @param delimiterChar
	 * @return
	 */
	public static String getALAsString(ArrayList arrayList, String delimiterChar){
		if(null == arrayList)
			return null;
		
		if(null == delimiterChar )
			delimiterChar = ", ";
		
		StringBuffer responseBuf = new StringBuffer("");
		for (Iterator iter = arrayList.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			if(!iter.hasNext())
				delimiterChar = "";
			responseBuf.append(line)
					.append(delimiterChar);
		}
		return responseBuf.toString();
	}

	/**
	 * returns the ArrayList as a single string 
	 * comprising of the arraylist elements separated by '\n'
	 * @param arrayList
	 * @return
	 */
	public static String getALAsStringsWithCR(ArrayList arrayList){
		return getALAsString(arrayList, "\n");
	}
	
	/**
	 * returns a new ArrayList created with a list of unique objects from
	 * the passed array list. Make sure that the underlying object implements 
	 * the equals method.
	 * 
	 * @param al the arraylist object from which duplicate objects need to be removed
	 * @return
	 */
	public static ArrayList removeDuplicates(ArrayList al){
		
		if(null == al || al.size() ==0 )
			return al;
		
		ArrayList newAl = new ArrayList();
		for (Iterator iter = al.iterator(); iter.hasNext();) {
			Object object = (Object) iter.next();
			if(! newAl.contains(object))
				newAl.add(object);
		}
		
		return newAl;
	}
}
