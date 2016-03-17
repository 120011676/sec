package com.github.q120011676.sec.hex.filter;

import com.github.q120011676.sec.core.filter.ResponseBufferedFilter;
import com.github.q120011676.sec.hex.Hex;

/**
 * Created by say on 3/2/16.
 */
public class HexResponseEncryptionBufferedFilter extends ResponseBufferedFilter {

    @Override
    public byte[] response(byte[] data) {
        return Hex.encode(data);
    }
}
