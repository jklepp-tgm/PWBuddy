package com.pwbuddy;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
 * @author Andreas Willinger, Jakob Klepp
 * @since 2013-05-28
 */

public class EncryptionCore {
    private SecretKeySpec key;
    private Cipher cipher;

    public EncryptionCore(String pkey)
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
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Verschlüsselt den angegebenen String mithile eines vorher festgelegten Masterpasswrotes
     *
     * @param input String, der verschlüsselt werden soll
     * @return
     *
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String encrypt(String input ) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.key);

        byte[] encryptedByte = this.cipher.doFinal(input.getBytes("UTF-8"));

        return new BASE64Encoder().encode(encryptedByte);
    }

    /**
     * Entschlüsselt den angegebenen String mithilfe des Masterpasswortes und des mitgegebenen Initialisierungs-Vektors
     * @param input String, der entschlüsselt werden soll
     * @param iv Initialisierungs-Vektor in String-format
     *
     * @return
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String decrypt(String input, String iv) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        this.cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(new BASE64Decoder().decodeBuffer(iv)));

        byte[] decryptedByte = this.cipher.doFinal(new BASE64Decoder().decodeBuffer(input));

        return new String(decryptedByte);
    }

    /**
     * @return Initialisierungs Vektor
     */
    public String getIV() {
        return new BASE64Encoder().encode(this.cipher.getIV());
    }
}
