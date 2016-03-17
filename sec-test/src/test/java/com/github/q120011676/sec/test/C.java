package com.github.q120011676.sec.test;

import com.github.q120011676.xhttp.XHttp;

import java.util.Base64;

/**
 * Created by say on 3/14/16.
 */
public class C {
    public static void main(String[] args) throws Exception {
        String data = "a=1&b=2&c=3";
//        data = "kkkkkkk";
        byte[] bs = Base64.getEncoder().encode(data.getBytes());
        String s = XHttp.url("http://localhost:8080/test?username=t").data(bs).post().dataToString();
        System.out.println(s);
    }
}


