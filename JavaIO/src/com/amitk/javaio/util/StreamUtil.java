package com.amitk.javaio.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

	/**
	 * @param args
	 */
	public static void copyToStream(InputStream in, OutputStream out, int bytesLength)
		throws IOException{
		byte[] bytes = new byte[bytesLength];
		//read from the Input Stream
		int bytesRead = 0;
		
		while(true){
			bytesRead = in.read(bytes);
			if(bytesRead == -1) 
				break;
			out.write(bytes, 0, bytesRead);
		}
		
		//write to the output stream
	}
}
