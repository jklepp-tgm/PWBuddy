package com.pwbuddy;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import javax.crypto.Cipher;

/**
 * Verschlüsselungs Kern
 *
 * @author Andreas Willinger
 * @since 2013-05-28
 */
public class PBEncryptionCore {
    private SecretKeySpec key;
    private Cipher cipher;

    public PBEncryptionCore(String pkey)
    {
        try
        {
            byte[] tKey = pkey.getBytes("UTF-8");

            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            // Schlüssel mit SHA1 Hashen
            tKey = sha.digest(tKey);
            // Erste 16 Bytes (128 Bit) kopieren, um immer dieselbe Länge zu haben
            tKey = Arrays.copyOf(tKey, 16);

            key = new SecretKeySpec(tKey, "AES");

            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String encrypt(String input ) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.key);

        byte[] encryptedByte = this.cipher.doFinal(input.getBytes("UTF-8"));

        return new BASE64Encoder().encode(encryptedByte);
    }

    public String decrypt(String input, String iv) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(new BASE64Decoder().decodeBuffer(iv)));

        byte[] decryptedByte = this.cipher.doFinal(new BASE64Decoder().decodeBuffer(input));

        return new String(decryptedByte);
    }

    public String getIV()
    {
        return new BASE64Encoder().encode(this.cipher.getIV());
    }
    public static void main(String[] args)
    {
        /*
        String pkey = "7ca8ed779908923a093a5843e5dacc2a4fc32f19c09d4ca53a6f6b83ad";

        try
        {
            // Privater Schlüssel in Byte-Array repräsentation
            byte[] key = pkey.getBytes("UTF-8");

            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            // Schlüssel mit SHA1 Hashen
            key = sha.digest(key);
            // Erste 16 Bytes (128 Bit) kopieren, um immer dieselbe Länge zu haben
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secret = new SecretKeySpec(key, "AES");

            Cipher secure = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Verschlüsselungs-Objekt mit geheimen Schlüssel initialisieren
            secure.init(Cipher.ENCRYPT_MODE, secret);

            // Verschlüsselung durchführen
            byte[] encrypted = secure.doFinal("Verschlüsselter Text".getBytes("UTF-8"));

            // Initialisierungs Vektor
            byte[] iv = secure.getIV();

            // Base64 repräsentation des verschlüsselten Passwortes
            String encPW = new BASE64Encoder().encode(encrypted);

            // Base64 repräsentation des IV
            String encIV = new BASE64Encoder().encode(iv);

            // *** DEBUG ***
            System.out.println("Encrypted String (byte[]): "+new String(encrypted));
            System.out.println("Encrypted String (Base64): "+encPW);

            RandomAccessFile file = new RandomAccessFile("test.txt", "rw");
            file.writeBytes(encPW);
            file.close();

            System.out.println("DECRYPTING:\n");

            // Entschlüsselung initialisieren mit Schlüssel & IV
            secure.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(new BASE64Decoder().decodeBuffer(encIV)));

            // Entschlüsseln
            byte[] original = secure.doFinal(new BASE64Decoder().decodeBuffer(encPW));

            String originalString = new String(original);
            System.out.println("Decrypted String: " + originalString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */
    }
}
