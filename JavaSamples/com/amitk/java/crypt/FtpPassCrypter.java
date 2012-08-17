package com.amitk.java.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 1/30/12
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class FtpPassCrypter {

    private static final String PASS_PHRASE = "C@sf79";
    private static final int ITERATION_COUNT = 23;
    private static final FtpPassCrypter crypter;
    static {
        crypter = new FtpPassCrypter(PASS_PHRASE);
    }

    static Logger logger = LoggerFactory.getLogger(FtpPassCrypter.class);



    private Cipher ecipher;
    private Cipher dcipher;

    private byte[] salt = {
            (byte)0x1E, (byte)0xAA, (byte)0xF9, (byte)0x02,
            (byte)0x42, (byte)0xBC, (byte)0x2E, (byte)0xCD
    };

    public FtpPassCrypter(String passPhrase) {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, ITERATION_COUNT);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);

            // Create the ciphers
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            logger.error("InvalidAlgorithmParameterException", e);
        } catch (java.security.spec.InvalidKeySpecException e) {
            logger.error("InvalidKeySpecException", e);
        } catch (javax.crypto.NoSuchPaddingException e) {
            logger.error("NoSuchPaddingException", e);
        } catch (java.security.NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        } catch (java.security.InvalidKeyException e) {
            logger.error("InvalidKeyException", e);
        }
    }


    public String encrypt(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = null;
            //Since we're using it as a single instance (For whatever reason), methods would need synchronization.
            synchronized(ecipher){
                enc = ecipher.doFinal(utf8);
            }

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {

        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public String decrypt(String str) {
        try {
            //logger.debug("Decrypting " + str);
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = null;
            //Since we're using it as a single instance (For whatever reason), methods would need synchronization.
            //Without this concurrent decryption was corrupting the results/throwing BadPadding Exception.
            synchronized(dcipher){
                utf8 = dcipher.doFinal(dec);
            }

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            logger.error("BadPadding Exception", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("IllegalBlockSizeException", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (java.io.IOException e) {
            logger.error("IOException", e);
        }
        logger.error("RETURNING NULL !!!!");
        return null;
    }

    
    public static void main(String[] args){

        logger.info("dec: {}", crypter.decrypt("QM5w6cHOBDuOtluYAMYlNA=="));
        logger.info("dec: {}", crypter.decrypt("lK+OYtaTcEo="));
        logger.info("dec: {}", crypter.decrypt("EUnVrFE+r6XzgiZQJ3eNHQ=="));
    }
}
