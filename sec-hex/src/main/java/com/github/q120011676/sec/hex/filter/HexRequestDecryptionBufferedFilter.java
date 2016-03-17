package com.github.q120011676.sec.hex.filter;

import com.github.q120011676.sec.core.filter.RequestBufferedFilter;
import com.github.q120011676.sec.hex.Hex;

/**
 * Created by say on 3/2/16.
 */
public class HexRequestDecryptionBufferedFilter extends RequestBufferedFilter {

    @Override
    public byte[] request(byte[] data) {
        return Hex.decode(data);
    }
}
