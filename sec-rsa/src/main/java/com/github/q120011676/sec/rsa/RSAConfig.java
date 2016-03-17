package com.github.q120011676.sec.rsa;

import java.io.File;
import java.io.FilenameFilter;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by say on 3/8/16.
 */
public class RSAConfig {
    private static final Properties PROPERTIES = new Properties();

    private static final Map<String, PublicKey> PUB_KEYS = new HashMap<>();
    private static RSAPrivateKey PRI_KEY;

    static {
        try {
            PROPERTIES.load(RSAConfig.class.getResourceAsStream("/rsa.properties"));
            PRI_KEY = OpenSSHRSAFile.getPrivateKey(RSAConfig.class.getResource(get("pri.filepath")).getPath());
            File[] files = new File(RSAConfig.class.getResource(get("pub.path")).getPath()).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".pub");
                }
            });
            if (files != null) {
                for (File f : files) {
                    PublicKey pub = OpenSSHRSAFile.getPublicKey(f);
                    String filename = f.getName();
                    PUB_KEYS.put(filename.substring(0, filename.indexOf(".")), pub);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String name) {
        return PROPERTIES.getProperty(name);
    }

    public static RSAPublicKey getPublicKey(String name) {
        return (RSAPublicKey) PUB_KEYS.get(name);
    }

    public static RSAPrivateKey getPrivateKey() {
        return PRI_KEY;
    }
}
