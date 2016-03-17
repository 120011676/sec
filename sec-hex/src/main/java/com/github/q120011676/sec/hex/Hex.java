package com.github.q120011676.sec.hex;

/**
 * Created by say on 3/15/16.
 */
public class Hex {
    public static byte[] decode(byte[] bs) {
        byte[] b = new byte[bs.length / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            byte c0 = bs[j++];
            byte c1 = bs[j++];
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(byte c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    public static byte[] encode(byte[] bs) {
        StringBuilder result = new StringBuilder();
        for (byte b : bs) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex);
        }
        return result.toString().getBytes();
    }
}
