package me.nnero.http;

/**
 * Author: NNERO
 * Time : 19:51 2017/10/20
 */
public interface ResponseWriter {

    void write(byte[] data);

    void write(String data);

    void write(byte[] data,int offset,int length);
}
