package com.github.q120011676.sec.base64.filter;

import com.github.q120011676.sec.core.filter.ResponseBufferedFilter;

import java.util.Base64;

/**
 * Created by say on 3/2/16.
 */
public class Base64ResponseEncryptionBufferedFilter extends ResponseBufferedFilter {

    @Override
    public byte[] response(byte[] data) {
        return Base64.getEncoder().encode(data);
    }
}
