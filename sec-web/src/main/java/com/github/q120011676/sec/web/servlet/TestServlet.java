package com.github.q120011676.sec.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by say on 3/14/16.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameterMap());
        System.out.println("a:" + req.getParameter("a"));
        String username = req.getParameter("username");
        System.out.println(username);
        String data = username;
        for (int i = 0; i < 500; i++) {
            data += "a";
        }
        resp.getWriter().write(data);
        resp.getWriter().write("\r\n");
        resp.getWriter().write("bbb");
        resp.getWriter().flush();
        resp.getWriter().close();
    }
}
