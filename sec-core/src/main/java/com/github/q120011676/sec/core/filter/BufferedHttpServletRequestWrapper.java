package com.github.q120011676.sec.core.filter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by say on 3/11/16.
 */
public class BufferedHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private ByteArrayInputStream bais;
    private Map<String, String[]> map = new HashMap<>();

    public BufferedHttpServletRequestWrapper(HttpServletRequest request, List<SecRequest> secRequests) {
        super(request);
        try (BufferedInputStream bin = new BufferedInputStream(super.getInputStream());
             ByteArrayOutputStream baout = new ByteArrayOutputStream()) {
            int n;
            byte[] bs = new byte[8196];
            while ((n = bin.read(bs)) != -1) {
                baout.write(bs, 0, n);
            }
            baout.flush();
            byte[] data = baout.toByteArray();
            for (SecRequest r : secRequests) {
                data = r.request(data);
            }
            this.bais = new ByteArrayInputStream(data);
            String type = super.getContentType();
            if (type == null || "application/x-www-form-urlencoded".equals(type)) {
                if (baout.size() > 0) {
                    String str = new String(data);
                    String[] array = str.split("&");
                    for (String kvStr : array) {
                        String[] kv = kvStr.split("=");
                        if (map.containsKey(kv[0])) {
                            String[] vs = map.get(kv[0]);
                            String[] tmp = new String[vs.length + 1];
                            System.arraycopy(vs, 0, tmp, 0, vs.length);
                            System.arraycopy(new String[]{kv[1]}, 0, tmp, vs.length, 1);
                            map.put(kv[0], tmp);
                            continue;
                        }
                        map.put(kv[0], new String[]{kv[1]});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.putAll(super.getParameterMap());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.map;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.map.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.map.get(name);
    }

    @Override
    public String getParameter(String name) {
        String[] vs = this.map.get(name);
        if (vs != null) {
            if (vs.length > 0) {
                return vs[0];
            }
        }
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream(super.getInputStream(), this.bais);
    }
}
