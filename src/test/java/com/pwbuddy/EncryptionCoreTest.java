package com.pwbuddy;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-28
 */
public class EncryptionCoreTest {
    private EncryptionCore encryptionCore;

    private String inputString;

    private String encryptedString;

    private String initVector;

    @Before
    public void before() throws Exception {
        String password = "abc123";
        this.encryptionCore = new EncryptionCore(password);
        this.inputString = "bla";
        this.encryptedString = "D1TSDJ7FS4Lq+tMzQSdSzg==";
        this.initVector = "MN5Du+gB0RyEtBU3QzJrTw==";
    }

    @After
    public void after() throws Exception {
    }

    /**
     * PBEncryptionCore#encrypt(String)
     *
     * Testet ob man wieder das gleiche bekommt wenn etwas ver und wieder entschlüsselt wird
     *
     * @throws Exception Muh ... PBEncryptionCore
     */
    @Test
    public void testEncrypt() throws Exception {
        String out = this.encryptionCore.encrypt(this.inputString);

        String initVector = this.encryptionCore.getIV();

        assertEquals("Teste Verschlüsselung", this.encryptionCore.decrypt(out, initVector), this.inputString);
    }

    /**
     * PBEncryptionCore#encrypt(String, String)
     *
     * @throws Exception Muh ... PBEncryptionCore
     */
    @Test
    public void testDecrypt() throws Exception {
        String out = this.encryptionCore.decrypt(this.encryptedString, this.initVector);
        assertTrue("Teste Entschlüsselung", this.inputString.equals(out));
    }
}
