package me.nnero.core;


import me.nnero.http.HttpRequest;
import me.nnero.http.HttpResponse;

import java.io.IOException;

/**
 * Author: NNERO
 * Time : 19:55 2017/10/20
 */
public interface Container {

    void process(HttpRequest request, HttpResponse response) throws IOException;

}
