package com.github.q120011676.sec.rsa.filter;

import com.github.q120011676.sec.core.filter.ResponseBufferedFilter;
import com.github.q120011676.sec.rsa.RSA;
import com.github.q120011676.sec.rsa.RSAConfig;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by say on 3/2/16.
 */
public class RSAResponseEncryptionBufferedFilter extends ResponseBufferedFilter {
    private String username;
    private HttpServletRequest req;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        req = (HttpServletRequest) servletRequest;
        username = req.getParameter(RSAConfig.get("parameter.username"));
        String name = req.getHeader("head.username");
        if (name != null) {
            username = name;
        }
        super.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public byte[] response(byte[] data) {
        try {
            return RSA.encode(data, RSAConfig.getPublicKey(username));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
