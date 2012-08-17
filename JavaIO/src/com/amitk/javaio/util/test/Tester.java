package com.amitk.javaio.util.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.amitk.javaio.util.NullOutputStream;
import com.amitk.javaio.util.StreamUtil;

public class Tester {

	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();
			InputStream is = getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, 1024);
			StreamUtil.copyToStream(bis, System.out, 1024);
			bis.close();
			is.close();
			System.out.println("TIME TAKEN (Millis):" + ((System.currentTimeMillis() - start )) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static InputStream getInputStream()
		throws IOException{
		//return getFileInputStream(new File("/home/logs/applogs/vinsight/vinsight.log"));
		return getURLInputStream(new URL("http://10.8.7.145:9001/VINSight"));
	}
	
	public static InputStream getFileInputStream(File file)
		throws IOException{
		return new FileInputStream(file);
	}
	
	public static InputStream getURLInputStream(URL url)
	throws IOException{
		return url.openStream();
	}
	
}
