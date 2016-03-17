package com.github.q120011676.sec.base64.filter;

import com.github.q120011676.sec.core.filter.RequestBufferedFilter;

import java.util.Base64;

/**
 * Created by say on 3/2/16.
 */
public class Base64RequestDecryptionBufferedFilter extends RequestBufferedFilter {

    @Override
    public byte[] request(byte[] data) {
        return Base64.getDecoder().decode(data);
    }
}
