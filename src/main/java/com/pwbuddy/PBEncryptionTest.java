package com.pwbuddy;

/**
 * Verschlüsselungs Kern - Test Klasse
 *
 * @author Andreas Willinger
 * @since 2013-05-28
 */
public class PBEncryptionTest {
    public static void main(String[] args)
    {
        PBEncryptionCore core = new PBEncryptionCore("abc123");

        try
        {
            String encrypted =   "D1TSDJ7FS4Lq+tMzQSdSzg==";
            String IV = "MN5Du+gB0RyEtBU3QzJrTw==";
            /*String enc = core.encrypt("bla");
            String iv = core.getIV();
            System.out.println(enc);
            System.out.println(iv);  */
            String dec = core.decrypt(encrypted, IV);
            System.out.println(dec);
        }
        catch(Exception e)
        {

        }
    }
}
