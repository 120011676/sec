package com.github.q120011676.sec.core.filter;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by say on 3/16/16.
 */
public class SecConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(SecConfig.class.getResourceAsStream("/sec-default.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String name) {
        return PROPERTIES.getProperty(name);
    }
}
