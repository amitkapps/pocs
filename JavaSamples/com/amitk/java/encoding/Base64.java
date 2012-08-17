package com.amitk.java.encoding;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Nov 23, 2010
 * Time: 12:08:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Base64 {

    private static byte[] decode(String base64) throws IOException {
        return new BASE64Decoder().decodeBuffer(base64);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Decoded:" );
        printBytes(decode("gACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQ="));
    }

    private static void printBytes(byte[] bytes){
        for(byte b : bytes){
            System.out.print(b);
        }
    }

}
