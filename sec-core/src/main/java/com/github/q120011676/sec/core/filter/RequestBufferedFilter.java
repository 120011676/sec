package com.github.q120011676.sec.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by say on 3/16/16.
 */
public abstract class RequestBufferedFilter implements Filter, SecRequest {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new BufferedHttpServletRequestWrapper((HttpServletRequest) servletRequest, this), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
