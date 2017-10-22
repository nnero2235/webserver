package me.nnero.http.exception;

/**
 * Author: NNERO
 * Time: 2017/10/22 22:53
 **/
public class BadRequestException extends RuntimeException{

    public BadRequestException(String msg){
        super(msg);
    }
}
