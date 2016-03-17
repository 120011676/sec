package com.github.q120011676.sec.core.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by say on 3/14/16.
 */
public class BufferedServletInputStream extends ServletInputStream {
    private ServletInputStream sin;
    private InputStream in;

    public BufferedServletInputStream(ServletInputStream sin, InputStream in) {
        this.sin = sin;
        this.in = in;
    }

    @Override
    public boolean isFinished() {
        return this.sin.isFinished();
    }

    @Override
    public boolean isReady() {
        return this.sin.isReady();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.sin.setReadListener(readListener);
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }
}
