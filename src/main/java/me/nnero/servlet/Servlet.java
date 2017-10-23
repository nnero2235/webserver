package me.nnero.servlet;

import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;

/**
 * Author: NNERO
 * Time : 16:48 2017/10/23
 */
public interface Servlet {

    void init();

    void destroy();

    void service(HttpRequest request, HttpResponse response);
}
