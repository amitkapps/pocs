package com.amitk.crypto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class HelloCrypto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			String password = "1292834750834705293847650823475923845-239485-23745089237450823745087234";
			String encoded = new sun.misc.BASE64Encoder().encode(md.digest(password.getBytes()));
			System.out.println(encoded);
			InputStream is = new ByteArrayInputStream(encoded.getBytes());
			new sun.misc.BASE64Decoder().decodeBuffer(is, System.out);
			System.out.flush();
			System.out.println("Done");
			is.close();
			
			System.out.println(DigestUtils.md5Hex("mc1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
