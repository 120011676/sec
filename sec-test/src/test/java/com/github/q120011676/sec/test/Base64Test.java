package com.github.q120011676.sec.test;

import com.github.q120011676.xhttp.XHttp;

import java.util.Base64;

/**
 * Created by say on 3/14/16.
 */
public class Base64Test {
    public static void main(String[] args) {
        String r = XHttp.url("http://localhost:8080/test").data("a", new String(Base64.getEncoder().encode("mm1".getBytes()))).post().dataToString();
        System.out.println(r);
    }
}
