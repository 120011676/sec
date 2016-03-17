package com.github.q120011676.sec.core.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by say on 3/14/16.
 */
public class BufferedServletOutputStream extends ServletOutputStream {
    private ServletOutputStream sout;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public BufferedServletOutputStream(ServletOutputStream sout, ByteArrayOutputStream baos) {
        this.sout = sout;
        this.baos = baos;
    }

    @Override
    public boolean isReady() {
        return this.sout.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        this.sout.setWriteListener(writeListener);
    }

    @Override
    public void write(int b) throws IOException {
        this.baos.write(b);
    }

    @Override
    public void flush() throws IOException {
        this.baos.flush();
    }
}
