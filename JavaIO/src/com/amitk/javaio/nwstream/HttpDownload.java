package com.amitk.javaio.nwstream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HttpDownload {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String urlString = "http://download.espdata.com/jato_matson.zip";
			String userName = "download";
			String password = "p43Jkm";
			OutputStream os = new FileOutputStream("jato_matson.jar");
			os = new BufferedOutputStream(os, 2048 * 4);
			HttpDownload httpDnld = new HttpDownload(urlString, userName, password);
			httpDnld.copyToStream(os, 256);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private URL downloadURL;
	private transient String userName;
	private transient String password;
	public HttpDownload(String url, String userName, String password)
		throws MalformedURLException{
		downloadURL = new URL(url);
		this.userName = userName;
		this.password = password;
	}

	public void copyToStream(OutputStream outputStream, int bufferSize)
		throws IOException{
		URLConnection urlConn = downloadURL.openConnection();
		String passwdString = userName + ":" + password;
		String encoding = new sun.misc.BASE64Encoder().encode(passwdString.getBytes());
		urlConn.setRequestProperty("Authorization", "Basic " + encoding);
		urlConn.setUseCaches(false);
		InputStream is = new BufferedInputStream(urlConn.getInputStream(), 256);
		ZipInputStream zis = new ZipInputStream(is);
		int counter = 0;
		int byteRead;
		long bytesRead = 0;
		ZipEntry entry = null;
		while(zis.getNextEntry() != null){
			while ((byteRead = zis.read()) != -1) {
				outputStream.write(byteRead);
				bytesRead++;
				if (bytesRead %(1024 * 1024) == 0) {
					counter++;
					System.out.println((counter * 1024)  + " Kbytes read.");
				}
			}
		}
		System.out.println("Finished copying");
		zis.close();
	} 
}
