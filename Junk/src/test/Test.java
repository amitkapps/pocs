package test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) throws Exception{
/*
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.en("H4NnQiehLgWuaBb5p7QBxQ==");
*/
        String passwd = "Matson123";


        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] encrypted = digest.digest(passwd.getBytes("UTF-8"));

        BASE64Encoder encoder = new BASE64Encoder();
        String encodedString = encoder.encode(encrypted);

        System.out.println("Decoded: " + new String(encodedString));
	}
}
