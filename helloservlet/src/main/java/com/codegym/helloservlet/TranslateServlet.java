package com.codegym.helloservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "TranslateServlet", urlPatterns = "/translate")
public class TranslateServlet extends HttpServlet {
    Map<String, String> maps;
    @Override
    public void init() throws ServletException {
        maps = new HashMap<>();
        maps.put("hello", "Xin chào");
        maps.put("apple", "Táo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        String str = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/translate\" method=\"post\">\n" +
                "      <div>\n" +
                "        <input name='txtSearch' type=\"text\"  />\n" +
                "        <button>Dịch</button>\n" +
                "      </div>\n" +
                "      <label>Kết quả: </label>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";

        printWriter.println(str);

         **/

        // RequestDispatcher: điều phối request
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/translate.jsp");
        requestDispatcher.forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
        String search = req.getParameter("txtSearch");


        String result = maps.get(search);

        String str = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/translate\" method=\"post\">\n" +
                "      <div>\n" +
                "        <input value='%s' name='txtSearch' type=\"text\"  />\n" +
                "        <button>Dịch</button>\n" +
                "      </div>\n" +
                "      <label>Kết quả: %s</label>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";

        String strResult = String.format(str,search, result);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(strResult);
         **/

        String txtSearch = req.getParameter("txtSearch");
        String result = maps.get(txtSearch);


        req.setAttribute("result", result);
        req.setAttribute("search", txtSearch);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/translate.jsp");
        requestDispatcher.forward(req, resp);
    }
}
