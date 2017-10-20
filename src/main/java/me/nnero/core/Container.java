package me.nnero.core;

import nnero.http.HttpRequest;
import nnero.http.HttpResponse;

/**
 * Author: NNERO
 * Time : 19:55 2017/10/20
 */
public interface Container {

    void process(HttpRequest request, HttpResponse response);

}
