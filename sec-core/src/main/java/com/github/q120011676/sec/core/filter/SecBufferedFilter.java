package com.github.q120011676.sec.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by say on 3/16/16.
 */
public abstract class SecBufferedFilter implements Filter {
    private static final List<SecRequest> SEC_REQUEST_LIST = new ArrayList<>();
    private static final List<SecResponse> SEC_RESPONSE_LIST = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String reqStr = filterConfig.getInitParameter("req");
        if (reqStr != null && !"".equals(reqStr)) {
            String[] reqArray = reqStr.trim().split(">");
            for (String classPath : reqArray) {
                try {
                    String n = SecConfig.get(classPath);
                    if (n != null) {
                        classPath = n;
                    }
                    SecRequest secReq = (SecRequest) Class.forName(classPath.trim()).newInstance();
                    SEC_REQUEST_LIST.add(secReq);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        String respStr = filterConfig.getInitParameter("resp");
        if (respStr != null && !"".equals(respStr)) {
            String[] reqArray = respStr.trim().split(">");
            for (String classPath : reqArray) {
                try {
                    String n = SecConfig.get(classPath);
                    if (n != null) {
                        classPath = n;
                    }
                    SecResponse secReq = (SecResponse) Class.forName(classPath.trim()).newInstance();
                    SEC_RESPONSE_LIST.add(secReq);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        BufferedHttpServletRequestWrapper dec = new BufferedHttpServletRequestWrapper((HttpServletRequest) servletRequest, SEC_REQUEST_LIST)
        BufferedHttpServletResponseWrapper enc = new BufferedHttpServletResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(dec, enc);
        byte[] dataWriter = enc.toWriter().getBytes();
        if (dataWriter.length > 0) {
            for (SecResponse r : SEC_RESPONSE_LIST) {
                dataWriter = r.response(dataWriter);
            }
            servletResponse.getWriter().write(new String(dataWriter));
        }
        byte[] dataOut = enc.toOutputStream();
        if (dataOut != null && dataOut.length > 0) {
            for (SecResponse r : SEC_RESPONSE_LIST) {
                dataOut = r.response(dataOut);
            }
            servletResponse.getOutputStream().write(dataOut);
        }
    }

    @Override
    public void destroy() {

    }
}
