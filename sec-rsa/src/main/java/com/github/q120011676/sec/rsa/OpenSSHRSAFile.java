package com.github.q120011676.sec.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

/**
 * Created by say on 3/2/16.
 */
public class OpenSSHRSAFile {

    private static KeyFactory KEY_FACTORY = null;

    static {
        Security.addProvider(new BouncyCastleProvider());
        try {
            KEY_FACTORY = KeyFactory.getInstance("RSA", "BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getContext(File filePath) {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(filePath)))) {
            return pemReader.readPemObject().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RSAPrivateKey getPrivateKey(String filePath) throws Exception {
        return getPrivateKey(new File(filePath));
    }

    public static RSAPrivateKey getPrivateKey(File priFile) throws Exception {
        byte[] bs = getContext(priFile);
        return bs != null ? (RSAPrivateKey) KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(bs)) : null;
    }

    private static int decodeUInt32(byte[] key, int startIndex) {
        byte[] test = Arrays.copyOfRange(key, startIndex, startIndex + 4);
        return new BigInteger(test).intValue();
    }

    public static RSAPublicKey getPublicKey(String filePath) throws Exception {
        return getPublicKey(new File(filePath));
    }

    public static RSAPublicKey getPublicKey(File pubFile) throws Exception {
        byte[] key;
        try (FileInputStream fout = new FileInputStream(pubFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] bs = new byte[8196];
            for (int n; (n = fout.read(bs)) != -1; ) {
                baos.write(bs, 0, n);
            }
            String publicStr = new String(baos.toByteArray());
            key = Base64.decode(publicStr.split(" ")[1]);
        }
        byte[] sshrsa = new byte[]{0, 0, 0, 7, 's', 's', 'h', '-', 'r', 's',
                'a'};
        int startIndex = sshrsa.length;
        int len = decodeUInt32(key, startIndex);
        startIndex += 4;
        byte[] peb = new byte[len];
        for (int i = 0; i < len; i++) {
            peb[i] = key[startIndex++];
        }
        BigInteger pe = new BigInteger(peb);
        len = decodeUInt32(key, startIndex);
        startIndex += 4;
        byte[] mdb = new byte[len];
        for (int i = 0; i < len; i++) {
            mdb[i] = key[startIndex++];
        }
        BigInteger md = new BigInteger(mdb);
        KeySpec ks = new RSAPublicKeySpec(md, pe);
        return (RSAPublicKey) KEY_FACTORY.generatePublic(ks);
    }
}
