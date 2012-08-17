package com.shared.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	
	public static final String NULL_STRING = "<NULL>";
	public static final String BLANK_STRING = "<BLANK>";
	
	
	public static String toSelectiveString(Object bean, String[] fieldNames, String fieldDelimiter)
		throws Exception{
		StringBuffer beanStrBuf = new StringBuffer();

		if (null == fieldNames) {
			Map map = BeanUtils.describe(bean);
			Set keys = map.keySet();
			keys.remove("class");
			fieldNames = new String[keys.size()];
			keys.toArray(fieldNames);
			Arrays.sort(fieldNames);
		}
		
		for (int fldCtr = 0; fldCtr < fieldNames.length; fldCtr++) {
			String fieldName = fieldNames[fldCtr];
			String fieldVal = BeanUtils.getProperty(bean, fieldName);
			if (null == fieldVal) {
				fieldVal = NULL_STRING;
			}
			else if ("".equals(fieldVal)) {
				fieldVal = BLANK_STRING;
			}
			if (fldCtr != 0)
				beanStrBuf.append(fieldDelimiter);

			beanStrBuf.append(fieldVal);
		}
		
		return beanStrBuf.toString();
	}
	
	
	public static ArrayList toSelectiveStringInAL(ArrayList beans, String[] fieldNames, String fieldDelimiter)
		throws Exception{
		if (null == fieldNames) {
			Map map = BeanUtils.describe(beans.get(0));
			Set keys = map.keySet();
			keys.remove("class");
			fieldNames = new String[keys.size()];
			keys.toArray(fieldNames);
			Arrays.sort(fieldNames);
		}
		
		ArrayList beansToString = new ArrayList(beans.size());
		beansToString.add(GenUtils.getCharDelimitedStr(fieldNames, fieldDelimiter));
		
		for (Iterator iter = beans.iterator(); iter.hasNext();) {
			beansToString.add(toSelectiveString(iter.next(), fieldNames, fieldDelimiter));
		}
		return beansToString;
	}
	
	/**
	 * First line is the field names
	 * 
	 * @param filePath
	 * @param fieldDelimiter
	 * @param nullString - string representation of nulls in the file, default = "null".
	 * @param beanClass
	 * @return and ArrayList of the instances of the passed class.
	 * @throws Exception
	 */
	public static ArrayList loadBeansFromFile(String filePath, String fieldDelimiter, String nullString, Class beanClass)
		throws Exception{
		if (null == nullString)
			nullString = "null";
		
		ArrayList beans = new ArrayList();
		ArrayList rows = FileUtil.getFileContentsAsAL(filePath);
		String[] fieldNames = null;
		Iterator rowsIter = rows.iterator();
		
		fieldNames = GenUtils.getTokensFromString((String)rowsIter.next(), fieldDelimiter);

		for (; rowsIter.hasNext();) {
			String row = (String) rowsIter.next();
			String[] fieldVals = getTokensFromString(row, fieldDelimiter);
			Object bean = beanClass.newInstance();
			
			for (int fldCtr = 0; fldCtr < fieldNames.length; fldCtr++) {
				String fieldVal = nullString.equalsIgnoreCase(fieldVals[fldCtr]) ? null :fieldVals[fldCtr]; 
				BeanUtils.setProperty(bean, fieldNames[fldCtr], fieldVal);
			}
			beans.add(bean);
		}
		return beans;
	}
	
	
	private static final String[] getTokensFromString(	String string,
														String tknDelimiter
													)throws Exception {
		StringTokenizer tkns;
		if (null == string)
			throw new Exception("Null String passed.");
		if (null == tknDelimiter)
			tkns = new StringTokenizer(string);
		else
			tkns = new StringTokenizer(string, tknDelimiter);
		String[] tknArr = new String[tkns.countTokens()];

		for (int i = 0; i < tknArr.length; i++) {
			String tknVal = tkns.nextToken();
			if (tknVal.equalsIgnoreCase(NULL_STRING))
				tknArr[i] = null;
			else if (tknVal.equalsIgnoreCase(BLANK_STRING))
				tknArr[i] = "";
			else 
				tknArr[i] = tknVal;
		}

		return tknArr;
	}

	
	
}
