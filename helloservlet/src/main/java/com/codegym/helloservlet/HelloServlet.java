package com.codegym.helloservlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;



    public void init() {
        System.out.println("Chạy vào hàm init");
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Chạy vào hàm doGet");
        //    response.setContentType("text/plain");
//    response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        System.out.println("GỌI HÀM SERVICE");
    }

    public void destroy() {
        System.out.println("Chạy hàm destroy: HỦY");
    }
}