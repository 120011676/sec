package com.github.q120011676.sec.base64.filter;

import com.github.q120011676.sec.core.filter.SecRequest;
import com.github.q120011676.sec.core.filter.SecResponse;

import java.util.Base64;

/**
 * Created by say on 3/2/16.
 */
public class Base64Sec implements SecRequest, SecResponse {

    @Override
    public byte[] request(byte[] data) {
        return Base64.getDecoder().decode(data);
    }

    @Override
    public byte[] response(byte[] data) {
        return Base64.getEncoder().encode(data);
    }
}
