package me.nnero.servlet;

import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;

/**
 * Author: NNERO
 * Time : 16:50 2017/10/23
 */
public abstract class HttpServlet implements Servlet{

    @Override
    public void init(){
    }

    @Override
    public void destroy(){
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if("GET".equals(request.method())){
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
