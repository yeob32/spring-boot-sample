package com.example.mvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("SecondServlet!!");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Test</title></head>");
        out.println("<body><h1>have a nice day!!</h1></body>");
        out.println("</html>");
        out.close();

    }
}
