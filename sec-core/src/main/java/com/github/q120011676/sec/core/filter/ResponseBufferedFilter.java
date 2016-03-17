package com.github.q120011676.sec.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by say on 3/16/16.
 */
public abstract class ResponseBufferedFilter implements Filter, SecResponse {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        BufferedHttpServletResponseWrapper enc = new BufferedHttpServletResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, enc);
        String dataWriter = enc.toWriter();
        if (dataWriter != null && dataWriter.length() > 0) {
            servletResponse.getWriter().write(new String(response(dataWriter.getBytes())));
        }
        byte[] dataOut = enc.toOutputStream();
        if (dataOut != null && dataOut.length > 0) {
            servletResponse.getOutputStream().write(response(dataOut));
        }
    }

    @Override
    public void destroy() {

    }
}
