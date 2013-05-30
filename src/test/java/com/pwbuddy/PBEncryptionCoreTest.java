package com.pwbuddy;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Jakob Klepp
 * @since 2013-05-28
 */
public class PBEncryptionCoreTest {
    private PBEncryptionCore encryptionCore;

    private String inputString;

    private String encryptedString;

    private String initVector;

    @Before
    public void before() throws Exception {
        String password = "abc123";
        this.encryptionCore = new PBEncryptionCore(password);
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
     * Funktioniert nicht Initialisierungsvektor Random
     * TODO Wieder aktivieren wenn IV festgelegt werden kann
     */
    //@Test
    public void testEncrypt() throws Exception {
        String out = this.encryptionCore.encrypt(this.inputString);
        assertSame("Teste Verschlüsselung", this.encryptedString, out);

        this.initVector = this.encryptionCore.getIV();
    }

    @Test
    public void testDecrypt() throws Exception {
        String out = this.encryptionCore.decrypt(this.encryptedString, this.initVector);
        assertTrue("Teste Entschlüsselung", this.inputString.equals(out));
    }
}
