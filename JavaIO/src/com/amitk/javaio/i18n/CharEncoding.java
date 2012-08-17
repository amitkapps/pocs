package com.amitk.javaio.i18n;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CharEncoding {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ArrayList al = readFile("C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\JavaIO\\src\\data\\GB2312-Latin.txt");
		String inputRaw = String.valueOf(al);
		String gbCharsetString = new String(inputRaw.getBytes("ISO-8859-1"), "GB2312");
		String utf8EncodedString = new String(inputRaw.getBytes("ISO-8859-1"), "UTF-16");
		
		saveFile("C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\JavaIO\\src\\data\\UTF-8Encoded.txt",
				"UTF-16",
				inputRaw);
		
	}

	public static ArrayList readFile(String fileName)throws Exception{
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(fis);
		ArrayList al = new ArrayList();
		String input = null;
		while((input = dis.readLine()) != null)
			al.add(input);
		return al;
	}
	
	public static void saveFile(String fileName, String charsetName, String data)throws Exception{
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName(charsetName));
		osw.write(data);
		osw.flush();
		osw.close();
	}
}