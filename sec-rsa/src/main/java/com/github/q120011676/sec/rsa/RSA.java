package com.github.q120011676.sec.rsa;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by say on 3/11/16.
 */
public class RSA {

    public static byte[] encode(byte[] data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int len = (publicKey.getModulus().bitLength() / 8) - 11;
        return doFinal(data, cipher, len);
    }

    public static byte[] encode(byte[] data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int len = (privateKey.getModulus().bitLength() / 8) - 11;
        return doFinal(data, cipher, len);
    }

    public static byte[] decode(byte[] data, RSAPublicKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int len = privateKey.getModulus().bitLength() / 8;
        return doFinal(data, cipher, len);
    }

    public static byte[] decode(byte[] data, RSAPrivateKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int len = publicKey.getModulus().bitLength() / 8;
        return doFinal(data, cipher, len);
    }

    private static byte[] doFinal(byte[] data, Cipher c, int len) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (int i = 0; i < data.length; i += len) {
                int n = data.length - i;
                if (n < len) {
                    len = n;
                }
                baos.write(c.doFinal(data, i, len));
            }
            baos.flush();
            return baos.toByteArray();
        }
    }
}
