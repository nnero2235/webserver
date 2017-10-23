package me.nnero.http.exception;

/**
 * Author: NNERO
 * Time : 16:31 2017/10/23
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg){
        super(msg);
    }
}
