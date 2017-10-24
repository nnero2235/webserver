package me.nnero.servlet.impl;

import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;
import me.nnero.servlet.HttpServlet;

import java.io.PrintWriter;

/**
 * Author: NNERO
 * Time : 19:19 2017/10/23
 */
public class NiceServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        PrintWriter pw = response.getWriter();
        pw.print("Hello world!");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
