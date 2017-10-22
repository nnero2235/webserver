package me.nnero.core;

/**
 * Author: NNERO
 * Time : 20:00 2017/10/20
 */
public interface HttpConnector {

    void startAndListen();

    void close();
}
