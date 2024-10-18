package org.example.userstask.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class PasswordUtils {
    private static byte[] encrypted;
    private static String encryptedtext;
    private static String decrypted;
    static String key = "Bar12345Bar12345Bar12345Bar12345";
    static SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");

    public static String encrypt(String pInput) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(pInput.getBytes());
            encryptedtext = DatatypeConverter.printBase64Binary(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedtext;
    }

    public static String decrypt(String pInput) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            encrypted = DatatypeConverter.parseBase64Binary(pInput);
            decrypted = new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }
}
