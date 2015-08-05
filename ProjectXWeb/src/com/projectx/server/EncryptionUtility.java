package com.projectx.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.apache.tomcat.util.codec.binary.Base64;



public class EncryptionUtility 
{
    static Key myKey;
    public final static Key generateKey() throws NoSuchAlgorithmException
    {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        kg.init(random);
 
        if(myKey == null)
            myKey = kg.generateKey();
        
        return myKey;
    }
   
    public static final String encrypt(final String message, final Key key, final IvParameterSpec iv) throws IllegalBlockSizeException,
    BadPaddingException, NoSuchAlgorithmException,
    NoSuchPaddingException, InvalidKeyException,
    UnsupportedEncodingException, InvalidAlgorithmParameterException 
    {   
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,iv);
        
        byte[] stringBytes = message.getBytes(); 
        
        byte[] raw = cipher.doFinal(stringBytes);
        
        return Base64.encodeBase64String(raw);
    } 
        
    public static final String decrypt(final String encrypted,final Key key, final IvParameterSpec iv) throws InvalidKeyException,
    NoSuchAlgorithmException, NoSuchPaddingException,
    IllegalBlockSizeException, BadPaddingException, IOException, InvalidAlgorithmParameterException
    {  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key,iv);
        
        byte[] raw = Base64.decodeBase64(encrypted);
        
        byte[] stringBytes = cipher.doFinal(raw);
            
        String clearText = new String(stringBytes, "UTF8");
        return clearText;
    }
}
