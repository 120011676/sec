package com.github.q120011676.sec.core.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by say on 3/14/16.
 */
public class BufferedHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private StringWriter writer = new StringWriter();
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public BufferedHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(this.writer);
    }

    public String toWriter() throws IOException {
        return this.writer.toString();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new BufferedServletOutputStream(super.getOutputStream(), this.baos);
    }

    public byte[] toOutputStream() {
        return this.baos.toByteArray();
    }
}
