package com.github.q120011676.sec.rsa.filter;

import com.github.q120011676.sec.core.filter.RequestBufferedFilter;
import com.github.q120011676.sec.rsa.RSA;
import com.github.q120011676.sec.rsa.RSAConfig;

/**
 * Created by say on 3/2/16.
 */
public class RSARequestDecryptionBufferedFilter extends RequestBufferedFilter {

    @Override
    public byte[] request(byte[] data) {
        try {
            return RSA.decode(data, RSAConfig.getPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
