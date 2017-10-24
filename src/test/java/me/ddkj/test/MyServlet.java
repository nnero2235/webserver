package me.ddkj.test;

import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;
import me.nnero.servlet.HttpServlet;

/**
 * Author: NNERO
 * Time : 19:19 2017/10/23
 */
public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.getWriter().print("你好");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
